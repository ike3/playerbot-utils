package org.playerbot.ai.db.commands;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.azeckoski.reflectutils.ReflectUtils;
import org.playerbot.ai.annotation.AnnotationProcessor;
import org.playerbot.ai.db.DbException;
import org.playerbot.ai.db.JdbcDatabase;
import org.playerbot.ai.db.JdbcDatabase.QueryBuilder;
import org.playerbot.ai.entity.Character;
import org.playerbot.ai.entity.CharacterHomebind;
import org.playerbot.ai.entity.CharacterInventory;
import org.playerbot.ai.entity.CharacterPet;
import org.playerbot.ai.entity.CharacterQuest;
import org.playerbot.ai.entity.CharacterReputation;
import org.playerbot.ai.entity.CharacterSkill;
import org.playerbot.ai.entity.CharacterSpell;
import org.playerbot.ai.entity.ItemInstance;
import org.playerbot.ai.entity.Spell;

import static org.playerbot.ai.Utils.asMap;

public class UpdateCommand extends AbstractCommand {
    private final Map<Long, Spell> availableSpells;

    public UpdateCommand(JdbcDatabase database) {
        super(database);
        availableSpells = asMap(database.selectAll(Spell.class), Spell.class, version);
    }

    public void update(Character character) throws DbException {
        long guid = database.queryForLong("SELECT max(guid) FROM characters WHERE name = ?", character.getName());

        if (guid == 0) {
            guid = 1 + database.queryForLong("SELECT max(guid) FROM characters");
            character.setGuid(guid);

            database.insert(Character.class, character);
            insertHomebind(character, guid);
            insertPets(character);
            insertInventory(character);
        }

        character.setGuid(guid);

        merge(character, CharacterReputation.class, "faction", "reputation", new MergeCondition<CharacterReputation>() {
            @Override
            public boolean apply(Map<Long, CharacterReputation> existing, CharacterReputation element) {
                return !existing.containsKey(element.getFaction()) ||
                        element.getStanding() > existing.get(element.getFaction()).getStanding();
            }
        });

        merge(character, CharacterSkill.class, "skill", "skills", new MergeCondition<CharacterSkill>() {
            @Override
            public boolean apply(Map<Long, CharacterSkill> existing, CharacterSkill element) {
                if (element.getValue() > maxSkill) {
                    element.setValue(maxSkill);
                }
                if (element.getMax() > maxSkill) {
                    element.setMax(maxSkill);
                }
                return !existing.containsKey(element.getSkill()) || element.getValue() > existing.get(element.getSkill()).getValue();
            }
        });

        merge(character, CharacterSpell.class, "spell", "spells", new MergeCondition<CharacterSpell>() {
            @Override
            public boolean apply(Map<Long, CharacterSpell> existing, CharacterSpell element) {
                return !existing.containsKey(element.getSpell()) && element.getDisabled() == 0 &&
                        availableSpells.containsKey(element.getSpell())
                        && availableSpells.get(element.getSpell()).getLevel() <= maxLevel;
            }
        }, new QueryBuilder() {
            @Override
            public String build(AnnotationProcessor annotationProcessor) {
                return String.format("SELECT %s FROM %s WHERE disabled = 0 AND guid = ?",
                        StringUtils.join(annotationProcessor.getColumnNames(), ","),
                        annotationProcessor.getTableName());
            }
        });

        merge(character, CharacterQuest.class, "quest", "quests", new MergeCondition<CharacterQuest>() {
            @Override
            public boolean apply(Map<Long, CharacterQuest> existing, CharacterQuest element) {
                return !existing.containsKey(element.getQuest()) && element.getStatus() == 1;
            }
        }, new QueryBuilder() {
            @Override
            public String build(AnnotationProcessor annotationProcessor) {
                return String.format("SELECT %s FROM %s WHERE status = 1 AND guid = ?",
                        StringUtils.join(annotationProcessor.getColumnNames(), ","),
                        annotationProcessor.getTableName());
            }
        });
    }

    private interface MergeCondition<T> {
        boolean apply(Map<Long, T> existing, T element);
    }

    private <T> void merge(Character character, Class<T> type, String valueField, String collectionField,
            MergeCondition<T> mergeOnlyIf) {
        Map<Long, T> existing = asMap(database.select(type, character.getGuid()), valueField, type);

        merge(character, type, valueField, collectionField, mergeOnlyIf, existing);
    }

    private <T> void merge(Character character, Class<T> type, String valueField, String collectionField,
            MergeCondition<T> mergeOnlyIf, QueryBuilder queryBuilder) {
        Map<Long, T> existing = asMap(database.select(type, new Object[] { character.getGuid() }, queryBuilder), valueField, type);

        merge(character, type, valueField, collectionField, mergeOnlyIf, existing);
    }

    private <T> void merge(Character character, Class<T> type, String valueField, String collectionField,
            MergeCondition<T> mergeOnlyIf, Map<Long, T> existing) {
        ReflectUtils utils = ReflectUtils.getInstance();
        AnnotationProcessor annotationProcessor = new AnnotationProcessor(type, version);
        for (T element : (Iterable<T>) utils.getFieldValue(character, collectionField)) {
            Long key = (Long) utils.getFieldValue(element, valueField);

            if (mergeOnlyIf.apply(existing, element)) {
                utils.setFieldValue(element, "guid", character.getGuid());

                database.update(String.format("DELETE FROM %s WHERE guid = ? AND %s = ?",
                        annotationProcessor.getTableName(), valueField), character.getGuid(), utils.getFieldValue(
                        element, valueField));
                database.insert(type, element);
            }
        }
    }


    private void insertHomebind(Character character, long guid) {
        if (character.getHomebind() != null) {
            character.getHomebind().setGuid(guid);
            database.insert(CharacterHomebind.class, character.getHomebind());
        }
    }

    private void insertInventory(Character character) {
        long firstItemGuid = 1 + database
                .queryForLong("SELECT MAX(guid) FROM (SELECT guid FROM item_instance UNION SELECT item as guid FROM character_inventory) q");
        long itemInstanceCounter = 0;
        for (ItemInstance itemInstance : character.getItemInstances()) {
            itemInstance.setOwner_guid(character.getGuid());
            itemInstance.setGuid(firstItemGuid + itemInstanceCounter++);
            database.insert(ItemInstance.class, itemInstance);
        }

        long inventoryCounter = 0;
        for (CharacterInventory inventory : character.getInventory()) {
            inventory.setGuid(character.getGuid());
            inventory.setItem(firstItemGuid + inventoryCounter++);
            database.insert(CharacterInventory.class, inventory);
        }
    }

    private void insertPets(Character character) {
        long firstPetId = 1 + database.queryForLong("SELECT MAX(id) FROM character_pet");
        for (CharacterPet pet : character.getPets()) {
            pet.setOwner(character.getGuid());
            pet.setId(firstPetId++);
            database.insert(CharacterPet.class, pet);
        }
    }
}
