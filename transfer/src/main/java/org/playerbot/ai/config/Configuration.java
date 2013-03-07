package org.playerbot.ai.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Configuration {

    @XmlElement(name = "database")
    private List<DatabaseConfiguration> databases;

    private transient Map<String, DatabaseConfiguration> databaseMap = new HashMap<String, DatabaseConfiguration>();

    public List<DatabaseConfiguration> getDatabases() {
        return databases;
    }

    public DatabaseConfiguration getDatabaseConfiguration(String version) throws ConfigurationException {
        if (databaseMap.isEmpty()) {
            for (DatabaseConfiguration config : databases) {
                databaseMap.put(config.getName(), config);
            }
        }

        if (!databaseMap.containsKey(version)) {
            throw new ConfigurationException(String.format("Connection configuration %s not found", version));
        }

        return databaseMap.get(version);
    }

    public static Configuration load(String fileName) throws ConfigurationException {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Configuration.class);
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(fileName));
            return (Configuration) ctx.createUnmarshaller().unmarshal(stream);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    public int size() {
        return databases.size();
    }
}
