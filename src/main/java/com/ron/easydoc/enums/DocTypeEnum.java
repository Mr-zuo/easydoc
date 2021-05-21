package com.ron.easydoc.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 接口文档类型枚举类
 * @author ron
 * @date 2021年05月21日
 */
@AllArgsConstructor
public enum DocTypeEnum {

    /**
     * controller包下接口
     */
    CONTROLLER("controller", "Rest_API"),

    /**
     * service包下接口
     */
    SERVICE("service", "Service_API"),

    /**
     * rpc服务包下接口
     */
    RPC_SERVICE("rpc.service", "RPC_API");

    @Getter
    @JsonValue
    private final String code;

    @Getter
    private final String name;

    public static BasicTypeEnum fromCode(String code) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getCode().equals(code)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal doc type: %s", code)));
    }

    public static BasicTypeEnum fromName(String name) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal doc type: %s", name)));
    }

    public static String toName(String code) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getCode().equals(code))
                .findFirst().map(BasicTypeEnum::getName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal doc type: %s", code)));
    }

    public static String toCode(String name) {
        return Arrays.stream(BasicTypeEnum.values()).filter(x -> x.getName().equals(name))
                .findFirst().map(BasicTypeEnum::getCode)
                .orElseThrow(() -> new IllegalArgumentException(String.format("illegal doc type: %s", name)));
    }
}
