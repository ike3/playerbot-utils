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
        ColumnMeta[] result = new ColumnMeta[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            result[i] = new ColumnMeta(fields[i], column != null ? column.value() : fields[i].getName());
        }
        return result;
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

    public Field[] getFields() {
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
            ReflectUtils.getInstance().setFieldValue(object, fieldName, value);
        } catch (Exception e) {
            throw new UnsupportedOperationException(String.format("Error setting field %s to %s", fieldName, value), e);
        }
    }

    public String getTableName() {
        return type.getAnnotation(Table.class).value();
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
