package com.ron.easydoc.annotation;


import java.lang.annotation.*;

/**
 * 该注解于类或接口的public方法，类或接口需提前被注解 {@link DocApi}标识
 * @author ron
 * @date 2021年05月20日
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DocMethod {
}
