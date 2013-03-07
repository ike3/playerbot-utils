package org.playerbot.ai.db.commands;

import org.playerbot.ai.db.JdbcDatabase;

public class AbstractCommand {
    protected final JdbcDatabase database;
    protected final String version;
    protected final long maxLevel, maxSkill;

    public AbstractCommand(JdbcDatabase database) {
        super();
        this.database = database;
        version = database.getConfig().getVersion();
        maxLevel = database.getConfig().getMaxLevel();
        maxSkill = database.getConfig().getMaxSkill();
    }
}
