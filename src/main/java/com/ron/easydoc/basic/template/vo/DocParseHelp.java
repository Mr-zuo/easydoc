package com.ron.easydoc.basic.template.vo;

import com.google.common.collect.Lists;
import com.ron.easydoc.basic.element.Comment;
import com.ron.easydoc.basic.element.Class;
import com.ron.easydoc.basic.element.Member;
import com.ron.easydoc.basic.parse.Parse;
import com.ron.easydoc.basic.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ron
 * @date 2021年05月20日
 */
public class DocParseHelp {

    static public List<DocPojoClass> autParseParamType(Comment.Tag tag, Member member){
        if(tag != null){
            List<DocPojoClass> docPojoClassList = new ArrayList<DocPojoClass>();

            if(!CollectionUtils.isEmpty(tag.getMetaData()) && tag.getMetaData().containsKey("type")){
                String[] paramTypes = tag.getMetaData().get("type").split(",");

                for(String paramType:paramTypes){
                    Class paramClass = Parse.autoParse(paramType.trim(), member);
                    if(paramClass != null){
                        docPojoClassList.add(new DocPojoClass(paramClass));
                    }
                }
                return docPojoClassList;
            }
        }

        Class paramClass = Parse.autoParse(member);
        if(paramClass != null){
            return Lists.newArrayList(new DocPojoClass(paramClass));
        }

        return null;

    }
}
