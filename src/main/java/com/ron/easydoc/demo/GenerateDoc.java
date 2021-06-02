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
//        execute("F:\\ron\\workspace\\private\\easydoc","F:\\ron\\workspace\\private\\easydoc","service","Service.java");
        execute("F:\\ron\\workspace\\private\\easydoc","F:\\ron\\workspace\\private\\easydoc","controller","Controller.java");
    }

    public static void execute(String sourcePath, String outPutPath,String targetPath,String suffix){
        GenConfig genConfig = new GenConfig();
        genConfig.setApiScanCommentTag(Lists.newArrayList("apiNote"));
        genConfig.setSourcePath(sourcePath);
        genConfig.setOutputPath(outPutPath);
//        genConfig.setWebType(GenConfig.WebType.SPRING_MVC);
//        genConfig.setOutPutType(GenConfig.OutPutType.SERVICE_HTML);
        genConfig.setTargetPath(targetPath);
        genConfig.setSuffix(suffix);
        GenDoc.gen(genConfig);
    }
}
