package com.ron.easydoc.basic.element;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Getter
@Setter
public class Class extends Member {

    private List<Field> fields;

    private List<Method> methods;

    private List<Class> innerClass;

    /**
     * interface位关键字，所以写为inerface
     */
    private boolean inerface;

}
