package org.playerbot.ai.config;

public class ConfigurationException extends Exception {
    private static final long serialVersionUID = 1L;

    public ConfigurationException(Exception e) {
        super(e);
    }

    public ConfigurationException(String msg) {
        super(msg);
    }
}
