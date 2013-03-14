package org.playerbot.ai;

import org.junit.Test;

public class UpdateTest extends AbstractDbUnitTest {

    @Test
    public void r2ToZero() throws Exception {
        replace();
        checkTables();
    }

    @Override
    protected String getExpectedDataSetName() {
        return "zero_updated.xml";
    }

    @Override
    protected String getSourceSetupDataSetName() {
        return "r2.xml";
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

    private void replace() throws Exception {
        Main main = new Main();
        main.characterName = "TestChar";
        main.sourceConnectionName = getSourceConnectionName();
        main.destinationConnectionName = getDestinationConnectionName();
        main.replace = false;
        main.configurationFileName = "src/main/resources/config.xml";
        main.run();
    }

}
