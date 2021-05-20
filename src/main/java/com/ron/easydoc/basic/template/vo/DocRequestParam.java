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
public class DocRequestParam {

    private String  name;

    private String type;  //参数类型

    private List<DocPojoClass> typeDoc; //对象类型的参数文档模型

    private String description;

    private String  defaultValue;

    private boolean isRequired;
}
