package org.playerbot.ai.db.commands;

import org.playerbot.ai.db.DbException;
import org.playerbot.ai.db.JdbcDatabase;
import org.playerbot.ai.entity.CharacterAchievement;
import org.playerbot.ai.entity.CharacterAchievementProgress;
import org.playerbot.ai.entity.CharacterAction;
import org.playerbot.ai.entity.CharacterGlyph;
import org.playerbot.ai.entity.CharacterHomebind;
import org.playerbot.ai.entity.CharacterInventory;
import org.playerbot.ai.entity.CharacterPet;
import org.playerbot.ai.entity.CharacterQuest;
import org.playerbot.ai.entity.CharacterQuestRewarded;
import org.playerbot.ai.entity.CharacterReputation;
import org.playerbot.ai.entity.CharacterSkill;
import org.playerbot.ai.entity.CharacterSpell;
import org.playerbot.ai.entity.CharacterTalent;
import org.playerbot.ai.entity.ItemInstance;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(JdbcDatabase database) {
        super(database);
    }

    public void delete(String characterName) throws DbException {
        long guid = database.queryForLong("SELECT max(guid) FROM characters WHERE name = ?", characterName);

        if (guid == 0) {
            return;
        }

        Class<?>[] types = new Class<?>[] { CharacterReputation.class, CharacterSpell.class,
                CharacterSkill.class, CharacterQuest.class, CharacterQuestRewarded.class, CharacterHomebind.class, CharacterInventory.class,
                ItemInstance.class, CharacterPet.class, CharacterTalent.class, CharacterAchievement.class,
                CharacterGlyph.class, CharacterAction.class, CharacterAchievementProgress.class };

        database.delete(org.playerbot.ai.entity.Character.class, characterName);
        for (Class<?> type : types) {
            database.delete(type, guid);
        }
    }

}
