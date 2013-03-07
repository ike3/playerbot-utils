package org.playerbot.ai;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.playerbot.ai.config.Configuration;
import org.playerbot.ai.db.Database;
import org.playerbot.ai.db.MangosDatabase;
import org.playerbot.ai.db.XmlDatabase;
import org.playerbot.ai.entity.Character;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class Main {
    @Parameter(names = "-config", description = "Configuration XML File Name", required = true)
    String configurationFileName;

    @Parameter(names = "-source", description = "Source Connection Name", required = true)
    String sourceConnectionName;

    @Parameter(names = "-destination", description = "Destination Connection Name", required = true)
    String destinationConnectionName;

    @Parameter(names = "-name", description = "Character Name", required = true)
    String characterName;

    @Parameter(names = "-replace", description = "Replace", required = false)
    Boolean replace = false;

    private Configuration config;
    private XmlDatabase xmlDatabase = new XmlDatabase();

    public static void main(String[] args) {
        Main main = new Main();
        try {
            new JCommander(main, args);
        } catch (ParameterException e) {
            reportError(e);
            return;
        }

        try {
            main.run();
        } catch (Exception e) {
            reportError(e);
        }
    }

    private static void reportError(Exception e) {
        System.err.println(WordUtils.wrap(e.getLocalizedMessage(), 80));
    }

    void run() throws Exception {
        config = Configuration.load(configurationFileName);

        Database source = connect(sourceConnectionName);
        Character character = source.select(characterName);

        Database destination = connect(destinationConnectionName);
        if (replace) {
            destination.delete(characterName);
        }
        destination.update(character);

        dumpXml();
    }

    private void dumpXml() {
        String xml = xmlDatabase.getData();
        if (!StringUtils.isEmpty(xml)) {
            System.out.println(xml);
        }
    }

    private Database connect(String connectionName) throws Exception {
        if ("xml".equalsIgnoreCase(connectionName))
            return xmlDatabase;

        return new MangosDatabase(config.getDatabaseConfiguration(connectionName));
    }
}