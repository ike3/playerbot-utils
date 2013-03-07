package org.playerbot.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.azeckoski.reflectutils.ReflectUtils;
import org.playerbot.ai.annotation.AnnotationProcessor;

public class Utils {

    public static <TK, T> Map<TK, T> asMap(List<T> list, Class<?> type, String version) {
        String keyFieldName = new AnnotationProcessor(type, version).getKey();
        return asMap(list, keyFieldName, type);
    }

    public static <TK, T> Map<TK, T> asMap(List<T> list, String keyFieldName, Class<?> type) {
        Map<TK, T> map = new HashMap<TK, T>();
        for (T element : list) {
            TK key = (TK)ReflectUtils.getInstance().getFieldValue(element, keyFieldName);
            map.put(key, element);

        }
        return map;
    }

}
