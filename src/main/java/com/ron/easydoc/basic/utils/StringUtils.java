package com.ron.easydoc.basic.utils;

/**
 * @author ron
 * @date 2021年05月20日
 */
public class StringUtils {

    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
