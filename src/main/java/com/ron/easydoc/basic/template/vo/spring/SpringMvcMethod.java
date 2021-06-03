package com.ron.easydoc.basic.template.vo.spring;

import com.google.common.collect.Lists;
import com.ron.easydoc.basic.element.*;
import com.ron.easydoc.basic.template.vo.DocApiMethod;
import com.ron.easydoc.basic.template.vo.DocParseHelp;
import com.ron.easydoc.basic.template.vo.DocPojoClass;
import com.ron.easydoc.basic.template.vo.DocRequestParam;
import com.ron.easydoc.enums.BasicTypeEnum;

import com.ron.easydoc.basic.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ron
 * @date 2021年05月20日
 */
public class SpringMvcMethod extends SpringMvcApiMember implements DocApiMethod {

    private List<DocRequestParam> requestParams;

    private String returnDesc;


    private List<DocPojoClass> returnTypes;

    private List<String> skipMethodParam = Lists.newArrayList(
            "HttpServletResponse",
            "HttpServletRequest",
            "Model",
            "ModelMap",
            "ModelAndView",
            "BindingResult"
    );


    public SpringMvcMethod(Method member) {
        super(member);

        //解析请求参数
        requestParams = parseRequestParams();

        //解析返回数据描述
        returnDesc = parseReturnDesc();

        //解析返回数据类型
        returnTypes = parseReturnType();
    }

    public List<DocRequestParam> getRequestParams() {
        return requestParams;
    }


    public String getReturnDesc() {
        return returnDesc;
    }

    @Override
    public String getMethodName() {
        return member.getName();
    }

    @Override
    public String getFullClsName() {
        return member.getPackageName() + "." + member.getParentMember().getName();
    }


    public List<DocPojoClass> getReturnTypes() {
        return returnTypes;
    }

    private Comment.Tag getReturnTag(){
        Comment comment = member.getComment();
        if(comment != null){
            return  comment.getTagByTagName("return");

        }

        return null;
    }

    private List<DocPojoClass> parseReturnType(){
        List<DocPojoClass> docPojoClassList;
        if (BasicTypeEnum.getCodes().contains(member.getType())){
            docPojoClassList = new ArrayList<DocPojoClass>();
            DocPojoClass pojoClass = new DocPojoClass();
            pojoClass.setType(member.getType());
            pojoClass.setDesc(BasicTypeEnum.toName(member.getType()));
            docPojoClassList.add(pojoClass);
        }else {
            docPojoClassList = DocParseHelp.autParseParamType(getReturnTag(), member);
        }
        return docPojoClassList;
    }


    private String parseReturnDesc(){
        Comment.Tag returnTag = getReturnTag();

        if(returnTag != null){
            return returnTag.getContent();
        }

        return "";
    }

    private boolean skipMethodParam(MethodParam methodParam, Comment.Tag tag){

        if(skipMethodParam.contains(methodParam.getType())){
            return true;
        }

        List<String> ignoreAnnotations = member.getGenConfig().getIgnoreApiAnnotationParam();
        if(!CollectionUtils.isEmpty(ignoreAnnotations) && !CollectionUtils.isEmpty(methodParam.getAnnotations())){
            List<String> annotations = methodParam.getAnnotations().stream().map((Annotation::getName)).collect(Collectors.toList());
            for(String ignoreAnnotation:ignoreAnnotations){
                if(annotations.contains(ignoreAnnotation)){
                    return true;
                }
            }
        }


        List<String> ignoreTypeParam = methodParam.getGenConfig().getIgnoreApiTypeParam();
        if(!CollectionUtils.isEmpty(ignoreTypeParam)){
            if(ignoreTypeParam.contains(methodParam.getType())){
                return true;
            }
        }

        //other skip condition
        return tag != null && StringUtils.isNotEmpty(tag.getContent()) && tag.getContent().contains("#ignore#");

    }

    private List<DocRequestParam> parseRequestParams(){
        List<MethodParam> params = ((Method)member).getParams();
        List<DocRequestParam>  requestParams = new ArrayList<DocRequestParam>();

        if(!CollectionUtils.isEmpty(params)){
            for(MethodParam methodParam:params){
                Annotation annotation  =  methodParam.getAnnotationByName("RequestParam");
                if(annotation == null){
                    annotation = methodParam.getAnnotationByName("PathVariable");
                }

                Comment.Tag paramTag =   member.getComment()!=null?member.getComment().getParamTagByName(methodParam.getName()):null;

                if(annotation == null && skipMethodParam(methodParam, paramTag)){
                    //跳过 非api参数的解析
                    continue;
                }

                DocRequestParam requestParam = new DocRequestParam();

                requestParam.setType(methodParam.getType());

                if(annotation == null || annotation instanceof MarkerAnnotation){

                    requestParam.setName(methodParam.getName());

                }else if(annotation instanceof SingleAnnotation){

                    requestParam.setName(((SingleAnnotation)annotation).getValue());
                    requestParam.setRequired(true);

                }else if(annotation instanceof NormalAnnotation){
                    NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;

                    String isRequire = normalAnnotation.getValue("required");

                    requestParam.setRequired(StringUtils.isEmpty(isRequire) || isRequire.equals("true"));

                    requestParam.setName(normalAnnotation.getValue("name"));

                    if(StringUtils.isEmpty(requestParam.getName())){
                        requestParam.setName(normalAnnotation.getValue("value"));
                    }
                    requestParam.setDefaultValue(normalAnnotation.getValue("defaultValue"));
                }
                if (paramTag != null) {
                    requestParam.setDescription(paramTag.getContent());
                }
                requestParam.setTypeDoc(DocParseHelp.autParseParamType(paramTag, methodParam));
                requestParams.add(requestParam);
            }

        }

        return requestParams;

    }


}
