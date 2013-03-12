package org.playerbot.ai;

import java.util.List;

import org.playerbot.ai.annotation.AnnotationProcessor;
import org.playerbot.ai.annotation.ColumnMeta;

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
                if (leftColumn.equals(rightColumn))
                    continue;

                if (rightColumn.isLinkedTo(leftColumn)) {
                    Object value = source.read(object, leftColumn);
                    destination.apply(object, rightColumn, value);
                }
            }
        }
    }

}
