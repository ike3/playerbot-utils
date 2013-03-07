package org.playerbot.ai;

import java.io.FileOutputStream;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DataSetExporter extends AbstractTest {

    public static void main(String[] args) throws Exception {
        DataSetExporter exporter = new DataSetExporter();
        exporter.loadConfig();
        exporter.run();
    }

    private void run() throws Exception {
        IDatabaseConnection connection = getConnection("mangoszero_Crystalsong");

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("characters", "SELECT * FROM characters WHERE name = 'TestChar'");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/tempDataSet.xml"));
    }
}
