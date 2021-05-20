package com.ron.easydoc.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试项目是否运行的类
 * @author ron
 * @date 2021年05月20日
 */

@RestController
@RequestMapping("condition")
public class ConditionController {

    /**
     * yml文件envirDetect配置值
     */
    @Value("${envirDetect}")
    private String envirDetect;

    /**
     * 获取系统环境
     * @return
     */
    @GetMapping("envir")
    public String envir(){
        return envirDetect;
    }

    /**
     * 系统是否正常运行
     * @return
     */
    @GetMapping("status")
    public boolean status(){
        return true;
    }
}
