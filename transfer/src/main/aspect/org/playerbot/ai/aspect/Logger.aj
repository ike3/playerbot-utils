package org.playerbot.ai.aspect;
import org.aspectj.lang.JoinPoint;
import org.playerbot.ai.Main;
import org.playerbot.ai.config.Configuration;
import org.playerbot.ai.db.Database;
import org.playerbot.ai.entity.Character;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public aspect Logger {
    private org.slf4j.Logger log(JoinPoint jp) {
        Object target = jp.getTarget();
        if (target == null) {
            target = jp.getThis();
        }
        return LoggerFactory.getLogger(target.getClass());
    }

    pointcut configLoaded(String fileName) :
        call(static Configuration Configuration.load(String)) && args(fileName);

    after(String fileName) returning(Configuration config): configLoaded(fileName) {
        log(thisJoinPoint).info("{} connection loaded from {}", config.size(), fileName);
    }

    pointcut databaseOpened(String connectionName) :
        call(Database Main.connect(String)) && args(connectionName);

    after(String connectionName) returning(Database db): databaseOpened(connectionName) {
        log(thisJoinPoint).info("{} connection opened", connectionName);
    }

    pointcut characterSelected(String characterName) :
        call(Character Database.select(String)) && args(characterName);

    after(String characterName) returning(Character c): characterSelected(characterName) {
        log(thisJoinPoint).info("{} character loaded", characterName);
    }

    pointcut characterUpdated(Character character) :
        call(void Database.update(Character)) && args(character);

    after(Character character) returning: characterUpdated(character) {
        log(thisJoinPoint).info("{} character updated", character.getName());
    }

    pointcut characterDeleted(String characterName) :
        call(void Database.delete(String)) && args(characterName);

    after(String characterName) returning: characterDeleted(characterName) {
        log(thisJoinPoint).info("{} character deleted", characterName);
    }

    pointcut statementPrepared(String sql) :
        call(* JdbcTemplate.*(String, ..)) && args(sql, ..);

    before(String sql): statementPrepared(sql) {
        LoggerFactory.getLogger("jdbc").debug(sql);
    }
}
