package com.ron.easydoc.basic.template.vo;

import java.util.List;

/**
 * @author ron
 * @date 2021年05月20日
 */
public interface DocApiMethod extends DocApiMember {

     List<DocRequestParam> getRequestParams();

     String getReturnDesc();

     String getMethodName();

     String getFullClsName();
}
