package com.ron.easydoc.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 常用基本类型，包装类，及String的枚举类，会随着项目的发展进一步扩展
 * @author ron
 * @date 2021年05月20日
 */
@AllArgsConstructor
public enum BasicTypeEnum {

    /**
     * 布尔类型--基本类型
     */
    bl("boolean", "布尔类型--基本类型"),

    /**
     * 布尔类--包装类
     */
    BL("Boolean", "布尔类--包装类"),

    /**
     * 整数类型--基本类型
     */
    in("int", "整数类型--基本类型"),

    /**
     * 整数类--包装类
     */
    IN("Integer", "整数类--包装类"),

    /**
     * 长整数类型--基本类型
     */
    lo("long", "长整数类型--基本类型"),

    /**
     * 长整数类--包装类
     */
    LO("Long", "长整数类型--包装类"),

    /**
     * 字符串类型
     */
    ST("String", "字符串类型");

    @Getter
    @JsonValue
    private final String code;

    @Getter
    private final String name;

    public static BasicTypeEnum fromCode(String code) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getCode().equals(code)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal item type: %s", code)));
    }

    public static BasicTypeEnum fromName(String name) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal item type: %s", name)));
    }

    public static String toName(String code) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getCode().equals(code))
                .findFirst().map(BasicTypeEnum::getName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal item type: %s", code)));
    }

    public static String toCode(String name) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getName().equals(name))
                .findFirst().map(BasicTypeEnum::getCode)
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal item type: %s", name)));
    }

    public static List<String> getCodes() {
        return Arrays.stream(BasicTypeEnum.values()).map(BasicTypeEnum::getCode).collect(Collectors.toList());
    }
}
