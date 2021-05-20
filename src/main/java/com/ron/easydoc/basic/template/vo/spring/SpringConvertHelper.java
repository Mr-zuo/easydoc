package com.ron.easydoc.basic.template.vo.spring;


import com.ron.easydoc.basic.element.Annotation;
import com.ron.easydoc.basic.element.Class;
import com.ron.easydoc.basic.element.Method;
import com.ron.easydoc.basic.template.vo.DocApiMethod;
import com.ron.easydoc.basic.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ron
 * @date 2021年05月20日
 */
public class SpringConvertHelper {

    private static boolean  isSpringAction(Method method){

        List<Annotation> annotations = method.getAnnotations();

        if(annotations != null && annotations.size() > 0){
            for(Annotation annotation:annotations){
                if(annotation.getName().equals("RequestMapping") ||
                annotation.getName().equals("GetMapping") ||
                annotation.getName().equals("DeleteMapping") ||
                annotation.getName().equals("PutMapping") ||
                annotation.getName().equals("PostMapping") ||
                annotation.getName().equals("DocMethod")){
                    return true;
                }
            }
        }
        return false;
    }

    public static SpringMvcApiClass convertToSpringMvcClass(Class cls){
        List<DocApiMethod> methodList = new ArrayList<DocApiMethod>();

        if(!CollectionUtils.isEmpty(cls.getMethods())){

            for(Method method:cls.getMethods()){
                if(method.isPublic() && isSpringAction(method)){
                    methodList.add(new SpringMvcMethod(method));
                }
            }
        }

        return new SpringMvcApiClass(methodList, cls);
    }
}
