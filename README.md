# easydoc
easydoc开源项目创建的初衷是更好的服务于项目开发，主要有以下几点：
    1.一键式创建接口文档
    2.实时生成最新的接口文档

easydoc的定位是工具，即util，而且对项目业务代码零侵入性，这样做到了开箱即用；

用法：
    public static void main(String[] args) {
        GenerateDoc.execute(String sourcePath, String outPutPath,String targetPackage,String suffix)
    }
    可以创建类似DocUtil类执行main方法，4个参数的含义分别为：
        sourcePath：需要扫描的源代码目录
        outPutPath：接口文档的输出目录
        targetPackage：目标文件所在package，默认值为service；若要生成restful api需传controller
        suffix：目标文件的后缀，默认值为Service.java；若要生成restful api需传Controller.java
        
规范：
    1.写好类注释，字段注释，方法注释，这样生成的文档才会有文字说明
    2.请求/响应参数需要在sourcePath路径下，这样才能生成详情

集成：
    1.引入依赖
        <dependency>
            <groupId>com.github.javaparser</groupId>
            <artifactId>javaparser-symbol-solver-core</artifactId>
            <version>3.5.16</version>
        </dependency>
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.23</version>
        </dependency> 
    2.easydoc项目打包后install到本地仓库，正常引用即可
    
规划：easydoc的定位很简单，就是针对项目生成各种文档，目前仅支持service，controller类型的文档，
    而且目前还有很多小问题，但本人在持续优化修复，希望以后会有更多的扩展可能和进步。
    
    
From Open Source, To Open Source.