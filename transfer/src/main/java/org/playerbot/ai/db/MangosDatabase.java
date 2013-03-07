package org.playerbot.ai.db;

import java.sql.SQLException;

import org.playerbot.ai.config.DatabaseConfiguration;
import org.playerbot.ai.db.commands.DeleteCommand;
import org.playerbot.ai.db.commands.SelectCommand;
import org.playerbot.ai.db.commands.UpdateCommand;
import org.playerbot.ai.entity.Character;

public class MangosDatabase extends JdbcDatabase {
    private final SelectCommand selectCommand;
    private final DeleteCommand deleteCommand;
    private final UpdateCommand updateCommand;

    public MangosDatabase(DatabaseConfiguration config) throws DbException {
        super(config);
        selectCommand = new SelectCommand(this);
        deleteCommand = new DeleteCommand(this);
        updateCommand = new UpdateCommand(this);
    }

    @Override
    public Character select(String characterName) throws DbException {
        return selectCommand.select(characterName);
    }

    @Override
    public void update(Character character) throws DbException {
        updateCommand.update(character);
    }


    @Override
    public void delete(String characterName) throws DbException {
        deleteCommand.delete(characterName);
    }

    @Override
    public void close() throws DbException {
        try {
            dataSource.close();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
