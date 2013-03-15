package org.playerbot.ai.r2_to_r2;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.junit.Test;
import org.playerbot.ai.AbstractDbUnitTest;

public class ReplaceTest extends AbstractDbUnitTest {

    @Test
    public void r2ToR2() throws Exception {
        process("replace");
        assertDestinationValid();
    }

    protected void checkTable(String tableName, String sql, String... ignoreColumns) throws Exception {
        ITable actualData = destinationConnection.createQueryTable(tableName,
                sql);
        Assertion.assertEqualsIgnoreCols(expectedDataSet.getTable(tableName), actualData, ignoreColumns);
    }

    @Override
    protected String getExpectedDataSetName() {
        return "r2_expected_replace.xml";
    }

    @Override
    protected String getSourceSetupDataSetName() {
        return "r2_setup.xml";
    }

    @Override
    protected String getDestinationConnectionName() {
        return "r2_Crystalsong";
    }

    @Override
    protected String getSourceConnectionName() {
        return "r2_Eversong";
    }
}
