package org.playerbot.ai.db.commands;

import org.apache.commons.lang.StringUtils;
import org.playerbot.ai.annotation.AnnotationProcessor;
import org.playerbot.ai.db.DbException;
import org.playerbot.ai.db.JdbcDatabase;
import org.playerbot.ai.db.JdbcDatabase.QueryBuilder;
import org.playerbot.ai.entity.Character;
import org.playerbot.ai.entity.CharacterAchievement;
import org.playerbot.ai.entity.CharacterHomebind;
import org.playerbot.ai.entity.CharacterInventory;
import org.playerbot.ai.entity.CharacterPet;
import org.playerbot.ai.entity.CharacterQuest;
import org.playerbot.ai.entity.CharacterReputation;
import org.playerbot.ai.entity.CharacterSkill;
import org.playerbot.ai.entity.CharacterSpell;
import org.playerbot.ai.entity.CharacterTalent;
import org.playerbot.ai.entity.ItemInstance;

public class SelectCommand extends AbstractCommand {

    public SelectCommand(JdbcDatabase database) {
        super(database);
    }

    public Character select(String characterName) throws DbException {
        Character character = database.selectOne(Character.class, characterName);
        if (character == null) {
            throw new DbException("Character " + characterName + " not found");
        }

        character.setHomebind(database.selectOne(CharacterHomebind.class, character.getGuid()));
        character.setPets(database.select(CharacterPet.class, character.getGuid()));

        character.setItemInstances(database.select(ItemInstance.class, new Object[] { character.getGuid() }, new QueryBuilder() {
            @Override
            public String build(AnnotationProcessor annotationProcessor) {
                return String.format("SELECT %s FROM %s inst "
                        + "INNER JOIN character_inventory inv on item = inst.guid " + "WHERE %s = ?",
                        StringUtils.join(annotationProcessor.getColumnNames("inst."), ","),
                        annotationProcessor.getTableName(), annotationProcessor.getKey());
            }
        }));
        character.setInventory(database.select(CharacterInventory.class, character.getGuid()));
        character.setReputation(database.select(CharacterReputation.class, character.getGuid()));
        character.setSpells(database.select(CharacterSpell.class, character.getGuid()));
        character.setSkills(database.select(CharacterSkill.class, character.getGuid()));
        character.setQuests(database.select(CharacterQuest.class, character.getGuid()));
        character.setTalents(database.select(CharacterTalent.class, character.getGuid()));
        character.setAchievements(database.select(CharacterAchievement.class, character.getGuid()));

        return character;
    }

}
