package com.ron.easydoc.basic;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.ron.easydoc.basic.config.GenConfig;
import com.ron.easydoc.basic.element.*;
import com.ron.easydoc.basic.element.Class;
import com.ron.easydoc.basic.parse.Parse;
import com.ron.easydoc.basic.template.TemplateParse;
import com.ron.easydoc.basic.template.vo.DocApiApiClass;
import com.ron.easydoc.basic.template.vo.rpc.RpcApiConvertHelper;
import com.ron.easydoc.basic.template.vo.spring.SpringConvertHelper;
import com.ron.easydoc.basic.utils.FileUtils;
import com.ron.easydoc.basic.utils.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springside.modules.utils.collection.ListUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ron
 * @since 2021/05/21
 */
@Slf4j
public class GenDoc {

    private GenConfig genConfig;

    public static void gen(GenConfig genConfig){
        new GenDoc(genConfig).gen();
    }

    public static void gen(String sourcePath, String outPath){
        GenConfig genConfig = new GenConfig();
        genConfig.setOutputPath(outPath);
        genConfig.setSourcePath(sourcePath);
        gen(genConfig);
    }

    public static void gen(String sourcePath, String outPath, List<String> scanApipackage){
        GenConfig genConfig = new GenConfig();
        genConfig.setOutputPath(outPath);
        genConfig.setSourcePath(sourcePath);
        genConfig.setApiScanPackage(scanApipackage);
        gen(genConfig);
    }


    public GenDoc(GenConfig genConfig){
        this.genConfig = genConfig;
    }

    public void gen(){
        //genConfig默认是service配置，如果是controller要配置
        if ("controller".equals(genConfig.getTargetPath())){
            genConfig.setOutPutType(GenConfig.OutPutType.RPC_HTML);
        }
        Stopwatch stopwatch  = Stopwatch.createStarted();
        log.info("【genDoc】校验配置！");
        if(!genConfig.validate()){
            //配置校验
            return;
        }
        log.info("【genDoc】配置校验完毕！");

        List<String> sourcePathRoot = genConfig.getSourcePathRoot();
        log.info("【genDoc】扫描源文件配置 sourcePathRoot:" + sourcePathRoot);

        String outPutPath = genConfig.getOutputPath();
        log.info("【genDoc】api文件输出路径配置 outPutPath:" + outPutPath);

        final String targetPath = genConfig.getTargetPath();
        log.info("【genDoc】指定服务接口所在package targetPath:" + targetPath);

        final String suffix = genConfig.getSuffix();
        log.info("【genDoc】指定服务接口文件后缀 suffix:" + suffix);

        List<DocApiApiClass> docClassList = new ArrayList<DocApiApiClass>();

        log.info("【genDoc】解析开始！");
        //扫描解析
        for(String path:sourcePathRoot){
            List<String> fileList = FileUtils.getJavaFileList(path);
            //并行解析
            fileList.stream()
                    .filter(o->o.contains(targetPath))//筛选所在package
                    .filter(o->o.endsWith(suffix))//筛选package内符合条件的文件
                    .forEach((filePath)->{
                Class cls;
                try {
                    cls = Parse.parse(new FileInputStream(filePath), genConfig);
                } catch (FileNotFoundException e) {
                    log.error("file not found!", e);
                    return;
                }
                if(GenConfig.WebType.SPRING_MVC.equals(genConfig.getWebType())){
                    DocApiApiClass docClass = parseSpringMvcClass(cls);
                    if(docClass != null){
                        docClassList.add(docClass);
                    }
                } else if (GenConfig.WebType.RPC_API.equals(genConfig.getWebType())) {
                    DocApiApiClass docClass = parseRpcApiClass(cls);
                    if(docClass != null){
                        docClassList.add(docClass);
                    }
                }
            });
        }
        log.info("【genDoc】 一共解析出" + docClassList.size() + "个api文件，耗时:" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "毫秒");
        if (ListUtil.isEmpty(docClassList)) return;

        if(GenConfig.OutPutType.HTML.equals(genConfig.getOutPutType()) ||
                GenConfig.OutPutType.RPC_HTML.equals(genConfig.getOutPutType()) || GenConfig.OutPutType.SERVICE_HTML.equals(genConfig.getOutPutType())){
            log.info("【genDoc】 开始生成html文档");
            genHtml(docClassList, outPutPath);
        }
        log.info("【genDoc】 解析完毕！共耗时:" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "毫秒");
    }

    private  DocApiApiClass parseSpringMvcClass(Class cls){
        boolean inPackage = false;

        if (!CollectionUtils.isEmpty(genConfig.getApiScanPackage())) {
            for (String packageName:genConfig.getApiScanPackage()){
                if (cls.getPackageName().startsWith(packageName)) {
                    inPackage = true;
                }
            }
        } else {
            inPackage = true;
        }

//        if(inPackage && cls.getAnnotationByName("Controller") != null || cls.getAnnotationByName("RestController") != null || cls.getAnnotationByName("DocApi") != null){
            if(!cls.ignore()){
                return SpringConvertHelper.convertToSpringMvcClass(cls);
            }
//        }
        return null;
    }

    private  DocApiApiClass parseRpcApiClass(Class cls){
        if(!cls.isInerface()){
            return null;
        }

        if(cls.ignore()){
            return null;
        }

        if (CollectionUtils.isEmpty(cls.getMethods())) {
            return null;
        }

        if (!CollectionUtils.isEmpty(genConfig.getApiScanPackage())) {
            for (String packageName:genConfig.getApiScanPackage()){
                if (cls.getPackageName().startsWith(packageName)) {
                    return RpcApiConvertHelper.convertToRpcApi(cls);
                }
            }
        } else {
            for (Method method:cls.getMethods()) {
                List<String> apiScanAnnotation = method.getGenConfig().getApiScanAnnotation();
                List<String> scanCommentTag = method.getGenConfig().getApiScanCommentTag();
                List<Annotation> annotations = method.getAnnotations();

                if (!CollectionUtils.isEmpty(apiScanAnnotation) && !CollectionUtils.isEmpty(annotations)) {
                    for (Annotation annotation:annotations) {
                        if (apiScanAnnotation.contains(annotation.getName())) {
                            return RpcApiConvertHelper.convertToRpcApi(cls);
                        }

                    }
                }

                if (!CollectionUtils.isEmpty(scanCommentTag) && method.getComment() != null
                        && !CollectionUtils.isEmpty(method.getComment().getTags())) {
                    for (Comment.Tag tag:method.getComment().getTags()) {
                        if (scanCommentTag.contains(tag.getTagName())) {
                            return RpcApiConvertHelper.convertToRpcApi(cls);
                        }
                    }
                }
            }
        }

        return null;
    }

    private  void genHtml(List<DocApiApiClass> docClassList, String outPutPath){
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData.put("jsonData", JSON.toJSONString(docClassList));
            mapData.put("classList", docClassList);
            String html = "";
            if (genConfig.getOutPutType().equals(GenConfig.OutPutType.HTML)) {
                html = TemplateParse.parseTemplate("html/index.ftl", mapData);
            } else if (genConfig.getOutPutType().equals(GenConfig.OutPutType.RPC_HTML)) {
                html = TemplateParse.parseTemplate("html/rpc.ftl", mapData);
            }else if (genConfig.getOutPutType().equals(GenConfig.OutPutType.SERVICE_HTML)) {
                html = TemplateParse.parseTemplate("html/service.ftl", mapData);
            }

            File fileDir = new File(outPutPath);
            File outFile = new File(outPutPath+"/doc.html");

            boolean resultFlag = true;
            if(!fileDir.exists()){
                resultFlag = fileDir.mkdirs();
            }

            if(!outFile.exists()){
                resultFlag = outFile.createNewFile();
            }

            if(!resultFlag){
                throw new Exception("create outFile `"+outFile+"` failure! Please ensure that the files exist and have read and write permissions.");
            }

            IOUtils.copy(html, new FileOutputStream(outFile));
            log.info("【genDoc】 html文档生成完成！ 耗时:"+stopwatch.elapsed(TimeUnit.MILLISECONDS)+"毫秒, 文档路径在:" + outFile);
        } catch (Exception e) {
            log.error("parsing failure!", e);
        }
    }
}
