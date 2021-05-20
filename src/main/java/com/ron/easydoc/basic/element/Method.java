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
public class Method  extends Member{

    private List<MethodParam> params;

}
