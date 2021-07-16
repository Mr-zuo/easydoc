package com.ron.easydoc.demo;

import com.google.common.collect.Lists;
import com.ron.easydoc.basic.GenDoc;
import com.ron.easydoc.basic.config.GenConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ron
 * @date 2021年05月13日
 */
@Slf4j
public class GenerateDoc {

    public static void main(String[] args) {
//        execute("/Users/10020093/ron/workspace/private/easydoc","/Users/10020093/ron/workspace/private/easydoc","service","Service.java");
        execute("/Users/10020093/ron/workspace/private/easydoc","/Users/10020093/ron/workspace/private/easydoc","controller",".java","DemoController");
//        execute("/Users/ron/workspace/easydoc","/Users/ron/workspace/easydoc","service","Service.java");
//        execute("/Users/ron/workspace/easydoc","/Users/ron/workspace/easydoc","controller","Controller.java");
    }

    public static void execute(String sourcePath, String outPutPath,String targetPackage,String suffix,String specifiedFileName){
        GenConfig genConfig = new GenConfig();
        genConfig.setApiScanCommentTag(Lists.newArrayList("apiNote"));
        genConfig.setSourcePath(sourcePath);
        genConfig.setOutputPath(outPutPath);
//        genConfig.setWebType(GenConfig.WebType.SPRING_MVC);
//        genConfig.setOutPutType(GenConfig.OutPutType.SERVICE_HTML);
        genConfig.setTargetPackage(targetPackage);
        genConfig.setSuffix(suffix);
        genConfig.setSpecifiedFileName(specifiedFileName);
        GenDoc.gen(genConfig);
    }
}
