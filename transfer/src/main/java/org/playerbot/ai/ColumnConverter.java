package org.playerbot.ai;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.playerbot.ai.annotation.AnnotationProcessor;
import org.playerbot.ai.annotation.ColumnMeta;
import org.playerbot.ai.annotation.MaxLength;
import org.playerbot.ai.annotation.SpaceSeparated;

public class ColumnConverter {

    private final String sourceVersion;
    private final String destinationVersion;

    public ColumnConverter(String sourceVersion, String destinationVersion) {
        this.sourceVersion = sourceVersion;
        this.destinationVersion = destinationVersion;
    }

    public void convert(Object object) {
        if (object instanceof List<?>) {
            for (Object element : ((List<?>)object)) {
                convert(element);
            }
            return;
        }

        AnnotationProcessor source = new AnnotationProcessor(object.getClass(), sourceVersion);
        AnnotationProcessor destination = new AnnotationProcessor(object.getClass(), destinationVersion);

        for (ColumnMeta leftColumn : source.getColumns()) {
            for (ColumnMeta rightColumn : destination.getColumns()) {
                Object value = source.read(object, leftColumn);

                if (leftColumn.equals(rightColumn)) {
                    destination.apply(object, rightColumn, processAnnotations(value, rightColumn));
                }
                else if (rightColumn.isLinkedTo(leftColumn)) {
                    destination.apply(object, rightColumn, value);
                }
            }
        }
    }

    private Object processAnnotations(Object value, ColumnMeta column) {
        if (value == null)
            return null;

        SpaceSeparated spaceSeparated = column.getField().getAnnotation(SpaceSeparated.class);
        if (spaceSeparated != null) {
            for (MaxLength length : spaceSeparated.value()) {
                if (destinationVersion.equals(length.version())) {
                    String[] values = value.toString().split("\\s");
                    return StringUtils.join(Arrays.copyOfRange(values, 0, Math.min(values.length, length.value())), " ");
                }
            }
        }

        return value;
    }

}
