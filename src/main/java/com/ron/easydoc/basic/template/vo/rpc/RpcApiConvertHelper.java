package com.ron.easydoc.basic.template.vo.rpc;



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
public class RpcApiConvertHelper {

    private static boolean  isRpcAction(Method method){

        if (method.getParentMember() instanceof Class) {
            Class cls = (Class) method.getParentMember();
            return cls.isInerface();
        }

        return false;
    }

    public static RpcApiClass convertToRpcApi(Class cls){
        List<DocApiMethod> methodList = new ArrayList<DocApiMethod>();

        if(!CollectionUtils.isEmpty(cls.getMethods())){

            for(Method method:cls.getMethods()){
                if(isRpcAction(method)){
                    methodList.add(new RpcMethod(method));
                }
            }
        }

        return new RpcApiClass(methodList, cls);
    }
}
