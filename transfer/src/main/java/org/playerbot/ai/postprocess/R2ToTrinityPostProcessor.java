package org.playerbot.ai.postprocess;

import java.util.Arrays;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.playerbot.ai.entity.Character;
import org.playerbot.ai.entity.CharacterGlyph;
import org.playerbot.ai.entity.CharacterQuest;
import org.playerbot.ai.entity.CharacterQuestRewarded;
import org.playerbot.ai.entity.ItemInstance;

public class R2ToTrinityPostProcessor implements PostProcessor {

    @Override
    public void postProcess(org.playerbot.ai.entity.Character character) {
        postProcessQuests(character);
        postProcessGlyphs(character);
        postProcessItems(character);
    }

    private void postProcessItems(Character character) {
        for (ItemInstance item : character.getItemInstances()) {
            if (item.getCreatorGuid() == 0) {
                item.setCreatorGuid(item.getOwner_guid());
            }

            // 0       1          2 3    4          5 6   7 8   9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27
            // 5231232 1191182336 3 2361 1065353216 0 712 0 712 0 0  0  0  0  1  0  0  0  0  0  0  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 25 25 0 0
            if (item.getData() != null) {
                String[] values = item.getData().split(" ");

                if (values.length > 3)
                    item.setItemEntry(Long.parseLong(values[3])); // OBJECT_FIELD_ENTRY = 3

                if (values.length > 10)
                    item.setCreatorGuid(Long.parseLong(values[10]));

                if (values.length > 12)
                    item.setGiftCreatorGuid(Long.parseLong(values[12]));

                if (values.length > 14)
                    item.setCount(Long.parseLong(values[14])); // FIELD_STACK_COUNT(6 + 8

                if (values.length > 15)
                    item.setDuration(Long.parseLong(values[15])); // ITEM_FIELD_DURATION

                if (values.length > 16)
                    item.setCharges(values[16]); // ITEM_FIELD_SPELL_CHARGES

                if (values.length > 21)
                    item.setFlags(Long.parseLong(values[21])); // ITEM_FIELD_FLAGS

                if (values.length > 57)
                    item.setEnchantments(StringUtils.join(Arrays.copyOfRange(values, 22, 57), " "));

                if (values.length > 59)
                    item.setRandomPropertyId(Long.parseLong(values[59])); // ITEM_FIELD_RANDOM_PROPERTIES_ID

                if (values.length > 62)
                    item.setPlayedTime(Long.parseLong(values[62]));// ITEM_FIELD_CREATE_PLAYED_TIME
            }

            if (item.getDurability() == 0) {
                item.setDurability(100);
            }
        }
    }

    private void postProcessGlyphs(org.playerbot.ai.entity.Character character) {
        CharacterGlyph result = new CharacterGlyph();
        result.setGuid(character.getGuid());
        for (int i = 0; i < character.getGlyphs().size(); i++) {
            result.setGlyphByIndex(i + 1, character.getGlyphs().get(i).getGlyph());
        }
        character.getGlyphs().clear();
        character.getGlyphs().add(result);
    }

    private void postProcessQuests(org.playerbot.ai.entity.Character character) {
        for (ListIterator<CharacterQuest> iterator = character.getQuests().listIterator(); iterator.hasNext();) {
            CharacterQuest quest = iterator.next();
            if (quest.isRewarded()) {
                CharacterQuestRewarded rewarded = new CharacterQuestRewarded();
                rewarded.setActive(1);
                rewarded.setGuid(quest.getGuid());
                rewarded.setQuest(quest.getQuest());
                character.getQuestRewarded().add(rewarded);
                iterator.remove();
            }
        }
    }

}
