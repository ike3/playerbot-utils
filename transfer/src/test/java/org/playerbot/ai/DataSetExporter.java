package org.playerbot.ai;

import java.io.FileOutputStream;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DataSetExporter extends AbstractTest {

    private static final String CONNECTION_NAME = "r2_Eversong";
    //private static final String CONNECTION_NAME = "mangoszero_Crystalsong";
    private static final String CHARACTER_NAME = "Eleya";

    public static void main(String[] args) throws Exception {
        DataSetExporter exporter = new DataSetExporter();
        exporter.loadConfig();
        exporter.run();
    }

    private void run() throws Exception {
        IDatabaseConnection connection = getConnection(CONNECTION_NAME);

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("characters",
                format("SELECT * FROM characters WHERE name = '$character'"));
        partialDataSet.addTable("character_homebind",
                format("SELECT t.* FROM character_homebind t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = '$character'"));
        partialDataSet.addTable("character_pet",
                format("SELECT t.* FROM character_pet t INNER JOIN characters c ON c.guid = t.owner WHERE c.name = '$character'"));
        partialDataSet.addTable("character_inventory",
                format("SELECT t.* FROM character_inventory t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = '$character'"));
        partialDataSet.addTable("item_instance",
                format("SELECT t.* FROM item_instance t INNER JOIN characters c ON c.guid = t.owner_guid WHERE c.name = '$character'"));
        partialDataSet.addTable("character_reputation",
                format("SELECT t.* FROM character_reputation t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = '$character'"));
        partialDataSet.addTable("character_skills",
                format("SELECT t.* FROM character_skills t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = '$character'"));
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/tempDataSet.xml"));
    }

    private String format(String sql) {
        return sql.replaceAll("\\$character", CHARACTER_NAME);
    }
}
