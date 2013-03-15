package org.playerbot.ai.r2_to_r2;

import org.junit.Test;
import org.playerbot.ai.AbstractDbUnitTest;

public class UpdateTest extends AbstractDbUnitTest {

    @Override
    protected void onBeforeSetup() throws Exception {
        process("delete");
    }

    @Test
    public void r2ToR2() throws Exception {
        process("update");
        assertDestinationValid();
    }

    @Override
    protected String getExpectedDataSetName() {
        return "r2_expected_update.xml";
    }

    @Override
    protected String getSourceSetupDataSetName() {
        return "r2_setup.xml";
    }

    @Override
    protected String getDestinationSetupDataSetName() {
        return "r2_destination_setup.xml";
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
