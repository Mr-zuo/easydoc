package com.ron.easydoc.basic.template.vo.spring;

import com.ron.easydoc.basic.element.Class;
import com.ron.easydoc.basic.template.vo.DocApiApiClass;
import com.ron.easydoc.basic.template.vo.DocApiMethod;

import java.util.List;

/**
 * @author ron
 * @date 2021年05月20日
 */
public class SpringMvcApiClass extends SpringMvcApiMember implements DocApiApiClass {
    private List<DocApiMethod> methods;

    private Class cls;

    public SpringMvcApiClass(List<DocApiMethod> methods, Class cls) {
        super(cls);
        this.methods = methods;
        this.cls = cls;
    }


    public List<DocApiMethod> getMethods() {
        return methods;
    }
}
