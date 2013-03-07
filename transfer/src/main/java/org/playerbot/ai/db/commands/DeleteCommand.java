package org.playerbot.ai.db.commands;

import org.playerbot.ai.db.DbException;
import org.playerbot.ai.db.JdbcDatabase;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(JdbcDatabase database) {
        super(database);
    }

    public void delete(String characterName) throws DbException {
        long guid = database.queryForLong("SELECT max(guid) FROM characters WHERE name = ?", characterName);

        if (guid == 0) {
            return;
        }

        String[] tables = new String[] { "characters", "character_reputation", "character_spell", "character_skills",
                "character_queststatus", "character_action", "character_aura", "character_battleground_data",
                "character_homebind", "character_inventory" };

        for (String table : tables) {
            database.update(String.format("DELETE FROM %s WHERE guid = ?", table), guid);
        }

        database.update("DELETE FROM character_pet WHERE owner = ?", guid);
        database.update("DELETE FROM mail WHERE receiver = ?", guid);
        database.update("DELETE FROM item_instance WHERE owner_guid = ?", guid);
    }

}
