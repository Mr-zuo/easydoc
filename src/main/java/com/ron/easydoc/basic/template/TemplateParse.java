package com.ron.easydoc.basic.template;

import com.ron.easydoc.basic.utils.IOUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Slf4j
public class TemplateParse {
    private static final String TEMPLATE_PREFIX = "/gendoc/template/";


    private static TemplateParse templateParse = new TemplateParse();
    private   Configuration configuration;
    private List<String> loadTemplateList = new ArrayList<String>();


    private Template getTemplate(String key) throws IOException {
        if(!loadTemplateList.contains(key) && loadTemplate(key)){
            loadTemplateList.add(key);
        }

        return configuration.getTemplate(key);
    }

    private boolean loadTemplate(String key) {
        String content;
        content = loadTemplateFromConfig(key);

        if (content == null) {
            content = loadTemplateFromDisk(key);
        }

        if (content == null) {
            log.error("gendoc parse key:" + key + "  template not found!");
            return false;
        } else {
            StringTemplateLoader stringTemplateLoader = (StringTemplateLoader) configuration.getTemplateLoader();
            stringTemplateLoader.putTemplate(key, content);
            return true;
        }
    }


    private String loadTemplateFromDisk(String key){
        try {
           return IOUtils.toString(new InputStreamReader(TemplateParse.class.getResourceAsStream(TEMPLATE_PREFIX+key)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String loadTemplateFromConfig(String key){
        return null;
    }

    private TemplateParse(){
        configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(stringTemplateLoader);
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.getVersion()));
        configuration.setDefaultEncoding("UTF-8");

        initTemplateCache();
    }


    public void initTemplateCache(){
        loadTemplate("html/jquery.js");
    }

    public static String parseTemplate(String templateKey, Class cls) throws Exception{
        Template template = getInstance().getTemplate(templateKey);

        StringWriter stringWriter = new StringWriter();

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("class", cls);

        template.process(dataMap, stringWriter);

        return stringWriter.toString();
    }

    public static String parseTemplate(String templateKey, Map<String, Object> data) throws Exception{
        Template template = getInstance().getTemplate(templateKey);

        StringWriter stringWriter = new StringWriter();

        template.process(data, stringWriter);

        return stringWriter.toString();
    }

    private static TemplateParse getInstance(){
        return templateParse;
    }



}
