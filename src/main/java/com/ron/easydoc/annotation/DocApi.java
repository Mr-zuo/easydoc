package com.ron.easydoc.annotation;

import java.lang.annotation.*;

/**
 * 该注解作用于类或接口
 * @author ron
 * @date 2021年05月20日
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DocApi {
}
