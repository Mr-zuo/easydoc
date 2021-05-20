package com.ron.easydoc.basic.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Getter
@Setter
@NoArgsConstructor
public class GenWebConfig {

    private Boolean  enabled = true;

    //文档路径，以当前项目的classPath为准
    private String  filePath = "/genDoc/out/html/";

    //默认请求的文件名称
    private String  defaultFile = "doc.html";

    //请求路径前缀
    private String  contextPath = "/genDoc";

    //启动端口
    private Integer port = 9086;
}
