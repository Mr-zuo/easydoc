package com.ron.easydoc.basic.template.vo.rpc;


import com.ron.easydoc.basic.element.*;
import com.ron.easydoc.basic.template.vo.DocApiMethod;
import com.ron.easydoc.basic.template.vo.DocParseHelp;
import com.ron.easydoc.basic.template.vo.DocPojoClass;
import com.ron.easydoc.basic.template.vo.DocRequestParam;
import com.ron.easydoc.basic.utils.CollectionUtils;
import com.ron.easydoc.basic.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ron
 * @date 2021年05月20日
 */
public class RpcMethod extends RpcApiMember implements DocApiMethod {

    private List<DocRequestParam> requestParams;

    private String returnDesc;


    private List<DocPojoClass> returnTypes;


    public RpcMethod(Method member) {
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
        return DocParseHelp.autParseParamType(getReturnTag(), member);
    }


    private String parseReturnDesc(){
        Comment.Tag returnTag = getReturnTag();

        if(returnTag != null){
            return returnTag.getContent();
        }

        return "";
    }

    private boolean skipMethodParam(MethodParam methodParam, Comment.Tag tag){

        List<String> ignoreAnnotations = member.getGenConfig().getIgnoreApiAnnotationParam();
        if(!CollectionUtils.isEmpty(ignoreAnnotations) && !CollectionUtils.isEmpty(methodParam.getAnnotations())){
            List<String> annotations = methodParam.getAnnotations().stream().map((Annotation::getName)).collect(Collectors.toList());
            for(String ignoreAnnotation:ignoreAnnotations){
                if(annotations.contains(ignoreAnnotation)){
                    return true;
                }
            }
        }


        List<String> ignoreTypeParam = member.getGenConfig().getIgnoreApiTypeParam();
        if(!CollectionUtils.isEmpty(ignoreTypeParam)){
            if(ignoreTypeParam.contains(methodParam.getType())){
                return true;
            }
        }



        //other skip condition
        if(tag != null && StringUtils.isNotEmpty(tag.getContent()) && tag.getContent().contains("#ignore#")){
            return true;
        }

        return false;
    }

    private List<DocRequestParam> parseRequestParams(){
        List<MethodParam> params = ((Method)member).getParams();
        List<DocRequestParam>  requestParams = new ArrayList<>();

        if(!CollectionUtils.isEmpty(params)){
            for(MethodParam methodParam:params){
                Comment.Tag paramTag =   member.getComment()!=null?member.getComment().getParamTagByName(methodParam.getName()):null;

                if(skipMethodParam(methodParam, paramTag)){
                    //跳过 非api参数的解析
                    continue;
                }

                DocRequestParam requestParam = new DocRequestParam();
                requestParam.setType(methodParam.getType());
                requestParam.setName(methodParam.getName());
                requestParam.setTypeDoc(DocParseHelp.autParseParamType(paramTag, methodParam));
                if (paramTag != null) {
                    requestParam.setDescription(paramTag.getContent());
                }
                requestParams.add(requestParam);
            }

        }

        return requestParams;

    }


}
