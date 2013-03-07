package org.playerbot.ai.db;

import org.playerbot.ai.entity.Character;

public interface Database {

    Character select(String characterName) throws DbException;

    void update(Character character) throws DbException;

    void delete(String characterName) throws DbException;

    void close() throws DbException;
}
