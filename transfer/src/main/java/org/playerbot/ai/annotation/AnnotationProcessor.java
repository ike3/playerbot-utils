package org.playerbot.ai.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.azeckoski.reflectutils.ReflectUtils;

public class AnnotationProcessor {

    private final Class<?> type;
    private final String version;

    public AnnotationProcessor(Class<?> type, String version) {
        this.type = type;
        this.version = version;
    }

    public ColumnMeta[] getColumns() {
        Field[] fields = getFields();
        List<ColumnMeta> result = new ArrayList<ColumnMeta>();
        for (int i = 0; i < fields.length; i++) {
            Columns columns = fields[i].getAnnotation(Columns.class);
            Column column = findColumnForVersion(columns);
            result.add(new ColumnMeta(fields[i], column != null ? column.value() : fields[i].getName()));
        }
        return result.toArray(new ColumnMeta[result.size()]);
    }

    private Column findColumnForVersion(Columns columns) {
        if (columns == null) {
            return null;
        }
        for (Column column : columns.value()) {
            List<String> asList = Arrays.asList(column.version());
            if (asList.contains("*") || asList.contains(version)) {
                return column;
            }
        }
        return null;
    }

    private Table findTableForVersion(Tables tables) {
        if (tables == null) {
            return null;
        }
        for (Table table : tables.value()) {
            List<String> asList = Arrays.asList(table.version());
            if (asList.contains("*") || asList.contains(version)) {
                return table;
            }
        }
        return null;
    }

    public String[] getColumnNames() {
        return getColumnNames("");
    }

    public String[] getColumnNames(String prefix) {
        ColumnMeta[] columns = getColumns();
        String[] result = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            result[i] = prefix + columns[i].getName();
        }
        return result;
    }

    private Field[] getFields() {
        List<Field> result = new ArrayList<Field>();
        for (Field field : type.getDeclaredFields()) {
            if (field.getAnnotation(Transient.class) != null) {
                continue;
            }

            For ver = field.getAnnotation(For.class);
            if (ver != null) {
                List<String> asList = Arrays.asList(ver.value());
                if (!asList.contains("*") && !asList.contains(version)) {
                    continue;
                }
            }
            result.add(field);
        }
        return result.toArray(new Field[result.size()]);
    }

    public String getKey() {
        for (Field field : type.getDeclaredFields()) {
            if (field.getAnnotation(Key.class) != null) {
                return field.getName();
            }
        }
        throw new UnsupportedOperationException("No key found in " + type.getName());
    }

    public void apply(Object object, ColumnMeta column, Object value) {
        String fieldName = column.getField().getName();
        try {
            ReflectUtils.getInstance().setFieldValue(object, fieldName, value, true);
        } catch (Exception e) {
            throw new UnsupportedOperationException(String.format("Error setting field %s to %s (%s)",
                    column.getField().toString(), value, value.getClass().toString()), e);
        }
    }

    public String getTableName() {
        Table table = type.getAnnotation(Table.class);
        if (table != null) {
            List<String> asList = Arrays.asList(table.version());
            if (asList.contains("*") || asList.contains(version)) {
                return table.value();
            }
        }

        Tables tables = type.getAnnotation(Tables.class);
        table = findTableForVersion(tables);
        if (table == null) {
            throw new UnsupportedOperationException("No tables found for " + type.toString());
        }
        return table.value();
    }

    public Object[] read(Object object, ColumnMeta[] columns) {
        Object[] data = new Object[columns.length];
        for (int i = 0; i < columns.length; i++) {
            data[i] = read(object, columns[i]);
        }
        return data;
    }

    public Object read(Object object, ColumnMeta column) {
        return ReflectUtils.getInstance().getFieldValue(object, column.getField().getName());
    }

    public boolean isEnabled() {
        For ver = type.getAnnotation(For.class);
        if (ver != null) {
            List<String> asList = Arrays.asList(ver.value());
            return asList.contains("*") || asList.contains(version);
        }
        return true;
    }
}
