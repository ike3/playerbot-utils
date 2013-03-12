package org.playerbot.ai.annotation;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(field.getName()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        ColumnMeta other = (ColumnMeta) obj;
        return new EqualsBuilder().append(name, other.name).append(field.getName(), other.field.getName()).isEquals();
    }

    public boolean isLinkedTo(ColumnMeta other) {
        return name.equals(other.name) && !field.getName().equals(other.field.getName());
    }

}
