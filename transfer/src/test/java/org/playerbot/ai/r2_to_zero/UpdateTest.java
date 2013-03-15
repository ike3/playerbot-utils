package org.playerbot.ai.r2_to_zero;

import org.junit.Test;
import org.playerbot.ai.AbstractDbUnitTest;

public class UpdateTest extends AbstractDbUnitTest {

    @Override
    protected void onBeforeSetup() throws Exception {
        process("delete");
    }

    @Test
    public void r2ToZero() throws Exception {
        process("update");
        assertDestinationValid();
    }

    @Override
    protected String getExpectedDataSetName() {
        return "zero_expected_update.xml";
    }

    @Override
    protected String getSourceSetupDataSetName() {
        return "r2_setup.xml";
    }

    @Override
    protected String getDestinationSetupDataSetName() {
        return "zero_setup.xml";
    }

    @Override
    protected String getDestinationConnectionName() {
        return "mangoszero_Crystalsong";
    }

    @Override
    protected String getSourceConnectionName() {
        return "r2_Eversong";
    }

}
