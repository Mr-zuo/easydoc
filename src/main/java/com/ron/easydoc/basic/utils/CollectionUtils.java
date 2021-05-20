package com.ron.easydoc.basic.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author ron
 * @date 2021年05月20日
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
