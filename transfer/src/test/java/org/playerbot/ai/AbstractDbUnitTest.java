package org.playerbot.ai;

import org.dbunit.Assertion;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

public abstract class AbstractDbUnitTest extends AbstractTest {
    protected IDatabaseConnection sourceConnection;
    protected IDatabaseConnection destinationConnection;
    protected IDataSet sourceDataSet;
    protected IDataSet destinationDataSet;
    protected IDataSet expectedDataSet;

    @Before
    public final void before() throws Exception {
        loadConfig();

        sourceConnection = getConnection(getSourceConnectionName());
        IDatabaseTester source = new DefaultDatabaseTester(sourceConnection);
        destinationConnection = getConnection(getDestinationConnectionName());
        IDatabaseTester destination = new DefaultDatabaseTester(destinationConnection);

        sourceDataSet = getDataSet(getSourceSetupDataSetName());
        source.setDataSet(sourceDataSet);
        source.setSetUpOperation(new CompositeOperation(DatabaseOperation.DELETE, DatabaseOperation.INSERT));

        if (getDestinationSetupDataSetName() != null) {
            destinationDataSet = getDataSet(getDestinationSetupDataSetName());
            destination.setDataSet(destinationDataSet);
            destination.setSetUpOperation(new CompositeOperation(DatabaseOperation.DELETE, DatabaseOperation.INSERT));
        }
        else {
            destination.setSetUpOperation(DatabaseOperation.NONE);
        }

        onBeforeSetup();

        source.onSetup();
        destination.onSetup();

        expectedDataSet = getDataSet(getExpectedDataSetName());
    }

    protected IDataSet getDataSet(String resourceName) throws Exception {
        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(resourceName));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        return replacementDataSet;
    }

    protected void assertDestinationValid() throws Exception {
        checkTable("characters", "SELECT * FROM characters WHERE name = 'TestChar'", "guid");

        checkTable("character_homebind",
                "SELECT t.* FROM character_homebind t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar'",
                "guid");

        checkTable("character_pet",
                "SELECT t.* FROM character_pet t INNER JOIN characters c ON c.guid = t.owner WHERE c.name = 'TestChar' ORDER BY t.id",
                "id", "owner");

        checkTable("character_inventory",
                "SELECT t.* FROM character_inventory t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar' ORDER BY t.slot",
                "guid","item");

        checkTable("item_instance",
                "SELECT t.* FROM item_instance t INNER JOIN characters c ON c.guid = t.owner_guid WHERE c.name = 'TestChar' ORDER BY t.guid",
                "guid", "owner_guid");

        checkTable("character_reputation",
                "SELECT t.* FROM character_reputation t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar' ORDER BY t.faction",
                "guid");

        checkTable("character_skills",
                "SELECT t.* FROM character_skills t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar' ORDER BY t.skill",
                "guid");

        checkTable("character_spell",
                "SELECT t.* FROM character_spell t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar' ORDER BY t.spell",
                "guid");

        checkTable("character_queststatus",
                "SELECT t.* FROM character_queststatus t INNER JOIN characters c ON c.guid = t.guid WHERE c.name = 'TestChar' ORDER BY t.quest",
                "guid");
    }

    private void checkTable(String tableName, String sql, String... ignoreColumns) throws Exception {
        destinationConnection = getConnection(getDestinationConnectionName());
        ITable actualData = destinationConnection.createQueryTable(tableName,
                sql);
        Assertion.assertEqualsIgnoreCols(expectedDataSet.getTable(tableName), actualData, ignoreColumns);
        destinationConnection.close();
    }

    protected void onBeforeSetup() throws Exception {
    }

    protected abstract String getExpectedDataSetName();
    protected abstract String getSourceSetupDataSetName();
    protected String getDestinationSetupDataSetName() { return null; }
    protected abstract String getDestinationConnectionName();
    protected abstract String getSourceConnectionName();

    protected void process(String mode) throws Exception {
        Main main = new Main();
        main.characterName = "TestChar";
        main.sourceConnectionName = getSourceConnectionName();
        main.destinationConnectionName = getDestinationConnectionName();
        main.mode = mode;
        main.configurationFileName = "src/main/resources/config.xml";
        main.run();
    }
}
