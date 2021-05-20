package com.ron.easydoc.basic.template.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Getter
@Setter
public class DocField {

    private String name;

    private String desc;

    private String type;

    private List<DocPojoClass> typeDoc; //对象类型的参数文档模型

    private boolean require;

    private String defaultValue;

}
