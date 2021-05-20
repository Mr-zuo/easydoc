package com.ron.easydoc.basic.element;


import lombok.Getter;
import lombok.Setter;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Getter
@Setter
public abstract class Annotation {

    private Member member;

    private String name;

}
