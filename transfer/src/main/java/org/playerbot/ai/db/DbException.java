package org.playerbot.ai.db;

public class DbException extends Exception {

    private static final long serialVersionUID = 1L;

    public DbException(Exception e) {
        super(e);
    }

    public DbException(String format, Object... params) {
        super(String.format(format, params));
    }

}
