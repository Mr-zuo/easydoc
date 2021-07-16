package com.ron.easydoc.basic.config;

import com.ron.easydoc.basic.utils.FileUtils;
import com.ron.easydoc.basic.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ron
 * @date 2021年05月20日
 */
@Getter
@Setter
@Slf4j
public class GenConfig {

    private static GenConfig genConfig = new GenConfig();

    private List<String> sourcePathRoot;

    private List<String> ignoreApiAnnotationParam;

    private List<String> ignoreApiTypeParam;

    private List<String> apiScanAnnotation;

    private List<String> apiScanCommentTag;

    private List<String> apiScanPackage;

    private String outputPath;
    
    private String targetPackage = "service";

    private String suffix = "Service.java";

    private String specifiedFileName;

    private WebType webType = WebType.SPRING_MVC;

    private OutPutType outPutType = OutPutType.SERVICE_HTML;


    public static GenConfig getGenConfig(){
        return genConfig;
    }


    //校验配置
    public boolean validate(){
        if(CollectionUtils.isEmpty(sourcePathRoot)){
            log.error("failure！ GenConfig.sourcePathRoot was required!");
            return false;
        }
        if(StringUtils.isEmpty(outputPath)){
            log.error("failure！ GenConfig.outputPath was required!");
            return false;
        }
        return true;
    }

    //解析的web框架类型
    public static enum WebType{
        SPRING_MVC,
        RPC_API;
    }

    //输出文档类型
    public static enum OutPutType{
        HTML,
        CONTROLLER_HTML,    //对应controller接口
        SERVICE_HTML,       //对应service接口
//        RPC_HTML,           //对应rpc接口
        MARKDOWN,
        WORD;

    }

    /**
     * 设置一个path 只能解析sourcePathRoot
     * @param path
     */
    public void setSourcePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException(path + " sourcePath 文件夹不存在!");
        }
        if (!file.isDirectory()) {
            throw new RuntimeException(path + " sourcePath 必须是个文件夹!");
        }
        if (this.sourcePathRoot == null) {
            this.sourcePathRoot = new ArrayList<>();
        }

        this.sourcePathRoot.addAll(FileUtils.getJavaSourcePath(path));

        if (CollectionUtils.isEmpty(this.sourcePathRoot)) {
            throw new RuntimeException(path + " 下没有可解析的java源文件目录！");
        }
    }
}
