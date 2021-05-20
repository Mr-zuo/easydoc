package com.ron.easydoc.demo;

import com.google.common.collect.Lists;
import com.ron.easydoc.basic.GenDoc;
import com.ron.easydoc.basic.config.GenConfig;
import com.ron.easydoc.basic.config.GenDocWebApp;
import com.ron.easydoc.basic.config.GenWebConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ron
 * @date 2021年05月13日 17:41
 */
@Slf4j
public class GenerateDoc {

    public static void main(String[] args) {
//        log.info("start");
//        genDoc(); //生成文档，
        execute("F:\\ron\\workspace\\private\\easydoc","F:\\ron\\workspace\\private\\easydoc");
    }

    private static void startWeb(){
        new GenDocWebApp(new GenWebConfig()).start();
    }

    /**
     * 生成文档
     * 根据自己项目的情况去配置
     */
    private static void genDoc(){
        GenConfig genConfig = new GenConfig();
        genConfig.setApiScanCommentTag(Lists.newArrayList("apiNote"));
        genConfig.setSourcePath("F:\\ron\\workspace\\private\\ron-api");
        genConfig.setOutputPath("F:\\ron\\workspace\\private\\ron-api");
        genConfig.setWebType(GenConfig.WebType.SPRING_MVC);
        genConfig.setOutPutType(GenConfig.OutPutType.RPC_HTML);
        GenDoc.gen(genConfig);
    }

    public static void execute(String sourcePath, String outPutPath){
        GenConfig genConfig = new GenConfig();
        genConfig.setApiScanCommentTag(Lists.newArrayList("apiNote"));
        genConfig.setSourcePath(sourcePath);
        genConfig.setOutputPath(outPutPath);
        genConfig.setWebType(GenConfig.WebType.SPRING_MVC);
        genConfig.setOutPutType(GenConfig.OutPutType.SERVICE_HTML);
        GenDoc.gen(genConfig);
    }
}
