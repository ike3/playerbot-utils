package org.playerbot.ai.annotation;

import java.lang.reflect.Field;

public class ColumnMeta {
    private Field field;
    private String name;

    public ColumnMeta(Field field, String name) {
        super();
        this.field = field;
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

}
