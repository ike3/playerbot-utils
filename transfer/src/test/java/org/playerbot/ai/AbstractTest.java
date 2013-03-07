package org.playerbot.ai;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlConnection;
import org.playerbot.ai.config.Configuration;
import org.playerbot.ai.config.ConfigurationException;
import org.playerbot.ai.config.DatabaseConfiguration;

public abstract class AbstractTest {
    protected Configuration config;

    protected void loadConfig() throws ConfigurationException {
        config = Configuration.load("src/main/resources/config.xml");
    }

    protected IDatabaseConnection getConnection(String version) throws Exception {
        DatabaseConfiguration dbConfiguration = config.getDatabaseConfiguration(version);
        String schema = dbConfiguration.getUrl().substring(dbConfiguration.getUrl().lastIndexOf('/') + 1);
        Connection conn = DriverManager.getConnection(dbConfiguration.getUrl(), dbConfiguration.getUsername(),
                dbConfiguration.getPassword());
        return new MySqlConnection(conn, schema);
    }

}
