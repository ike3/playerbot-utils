package org.playerbot.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.playerbot.ai.config.Configuration;
import org.playerbot.ai.config.ConfigurationException;
import org.playerbot.ai.db.Database;
import org.playerbot.ai.db.DbException;
import org.playerbot.ai.db.MangosDatabase;
import org.playerbot.ai.db.XmlDatabase;
import org.playerbot.ai.entity.Character;
import org.playerbot.ai.postprocess.PostProcessor;
import org.playerbot.ai.postprocess.R2ToTrinityPostProcessor;

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
    List<String> characterNames = new ArrayList<String>();

    @Parameter(names = "-mode", description = "Mode (update,replace,delete)", required = false)
    String mode = "update";

    private Configuration config;
    private XmlDatabase xmlDatabase = new XmlDatabase();
    private Database destination;
    private Database source;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        try {
            new JCommander(main, args);
        } catch (ParameterException e) {
            reportError(e);
            return;
        }

        main.run();
    }

    void run() throws Exception {
        connect();

        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        List<Future<?>> futures = new ArrayList<Future<?>>();
        for (final String characterName : characterNames) {
            futures.add(es.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Main.this.run(characterName);
                    } catch (Exception e) {
                        reportError(e);
                    }
                }
            }));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        es.shutdown();
    }

    private static void reportError(Exception e) {
        System.err.println(WordUtils.wrap(e.getLocalizedMessage(), 80));
    }

    void connect() throws Exception {
        config = Configuration.load(configurationFileName);
        destination = connect(destinationConnectionName);
        source = connect(sourceConnectionName);
    }

    void run(String characterName) throws Exception {
        Character destinationCharacter = null;

        if ("replace".equals(mode) || "delete".equals(mode)) {
            destinationCharacter = destination.select(characterName);
            destination.delete(characterName);
        }

        if ("replace".equals(mode) || "update".equals(mode)) {
            Character character = source.select(characterName);
            if (character == null) {
                throw new DbException("Character " + characterName + " not found");
            }

            if (destinationCharacter != null) {
                character.setAccount(destinationCharacter.getAccount());
            }

            linkColumns(character);
            postProcess(character);
            destination.update(character);
        }

        dumpXml();
    }

    private void postProcess(Character character) throws ConfigurationException {
        PostProcessor postProcessor = getPostProcessor();
        if (postProcessor != null) {
            postProcessor.postProcess(character);
        }
    }

    private PostProcessor getPostProcessor() throws ConfigurationException {
        String sourceVersion = config.getDatabaseConfiguration(sourceConnectionName).getVersion();
        String destinationVersion = config.getDatabaseConfiguration(destinationConnectionName).getVersion();

        if ("r2".equals(sourceVersion) && "tc".equals(destinationVersion)) {
            return new R2ToTrinityPostProcessor();
        }

        return null;
    }

    private void linkColumns(Character character) throws Exception {
        String sourceVersion = config.getDatabaseConfiguration(sourceConnectionName).getVersion();
        String destinationVersion = config.getDatabaseConfiguration(destinationConnectionName).getVersion();
        ColumnConverter converter = new ColumnConverter(sourceVersion, destinationVersion);
        converter.convert(character);
        converter.convert(character.getHomebind());
        converter.convert(character.getPets());
        converter.convert(character.getItemInstances());
        converter.convert(character.getInventory());
        converter.convert(character.getReputation());
        converter.convert(character.getSpells());
        converter.convert(character.getSkills());
        converter.convert(character.getQuests());
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
