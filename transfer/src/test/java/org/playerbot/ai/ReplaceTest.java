package org.playerbot.ai;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.junit.Test;

public class ReplaceTest extends AbstractDbUnitTest {

    @Test
    public void r2ToZero() throws Exception {
        replace();

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

    protected void checkTable(String tableName, String sql, String... ignoreColumns) throws Exception {
        ITable actualData = destinationConnection.createQueryTable(tableName,
                sql);
        Assertion.assertEqualsIgnoreCols(expectedDataSet.getTable(tableName), actualData, ignoreColumns);
    }

    @Override
    protected String getExpectedDataSetName() {
        return "zero.xml";
    }

    @Override
    protected String getSourceSetupDataSetName() {
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
