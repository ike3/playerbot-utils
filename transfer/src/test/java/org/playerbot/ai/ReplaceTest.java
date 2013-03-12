package org.playerbot.ai;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.junit.Test;

public class ReplaceTest extends AbstractDbUnitTest {

    @Test
    public void r2ToZero() throws Exception {
        replace();

        ITable actualData = destinationConnection.createQueryTable("characters",
                "SELECT * FROM characters WHERE name = 'TestChar'");
        Assertion.assertEqualsIgnoreCols(destinationDataSet.getTable("characters"), actualData, new String[] {"guid"});

        actualData = destinationConnection.createQueryTable("character_homebind",
                "SELECT t.* FROM character_homebind t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar'");
        Assertion.assertEqualsIgnoreCols(destinationDataSet.getTable("character_homebind"), actualData, new String[] {"guid"});

        actualData = destinationConnection.createQueryTable("character_pet",
                "SELECT t.* FROM character_pet t INNER JOIN characters c ON c.guid = t.owner WHERE c.name = 'TestChar' ORDER BY t.id");
        Assertion.assertEqualsIgnoreCols(destinationDataSet.getTable("character_pet"), actualData, new String[] {"id", "owner"});

        actualData = destinationConnection.createQueryTable("character_inventory",
                "SELECT t.* FROM character_inventory t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar' ORDER BY t.slot");
        Assertion.assertEqualsIgnoreCols(destinationDataSet.getTable("character_inventory"), actualData, new String[] {"guid","item"});

        actualData = destinationConnection.createQueryTable("item_instance",
                "SELECT t.* FROM item_instance t INNER JOIN characters c ON c.guid = t.owner_guid WHERE c.name = 'TestChar' ORDER BY t.guid");
        Assertion.assertEqualsIgnoreCols(destinationDataSet.getTable("item_instance"), actualData, new String[] {"guid", "owner_guid"});
    }

    @Override
    protected String getDestinationDataSetName() {
        return "zero.xml";
    }

    @Override
    protected String getSourceDataSetName() {
        return "r2.xml";
    }

    @Override
    protected String getDestinationConnectionName() {
        return "mangoszero_Crystalsong";
    }

    @Override
    protected String getSourceConnectionName() {
        return "r2_Eversong";
    }

    private void replace() throws Exception {
        Main main = new Main();
        main.characterName = "TestChar";
        main.sourceConnectionName = getSourceConnectionName();
        main.destinationConnectionName = getDestinationConnectionName();
        main.replace = true;
        main.configurationFileName = "src/main/resources/config.xml";
        main.run();
    }

}
