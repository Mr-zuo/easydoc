package com.ron.easydoc.basic.config;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Slf4j
public class GenDocWebApp {

    private GenWebConfig genConfig;

    public GenDocWebApp(GenWebConfig genConfig){
        this.genConfig = genConfig;
    }

    public void start(){
        if(!genConfig.getEnabled()){
            log.info("GenDocWebApp is disabled！");
            return;
        }

        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(genConfig.getPort()), 0);
        } catch (IOException e) {
            log.error("GenDocWebApp start failed", e);
            return;
        }

        httpServer.createContext(genConfig.getContextPath(), new HttpHandler() {
            public void handle(HttpExchange httpExchange) throws IOException {
                String path = httpExchange.getRequestURI().getPath().replace(genConfig.getContextPath(), "");

                if(path !=null && path.length() > 0){
                    if(path.startsWith("/")){
                        path = path.substring(1);
                    }
                }

                if(path == null || path.isEmpty()){
                    path = genConfig.getDefaultFile();
                }

                String requestFilePath = genConfig.getFilePath()+path;
                try {
                    String result = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(requestFilePath)))
                            .lines().collect(Collectors.joining(System.lineSeparator()));
                    byte[] respContents = result.getBytes("UTF-8");
                    httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                    httpExchange.sendResponseHeaders(200, respContents.length);
                    httpExchange.getResponseBody().write(respContents);
                }catch (Exception e){
                    log.error("e:",e);
                }finally {
                    httpExchange.close();
                }
            }
        });

        httpServer.start();
        log.info("GenDocWebApp startup success! port:" + genConfig.getPort());
    }

    public static void main(String[] args){
         new GenDocWebApp(new GenWebConfig()).start();
    }

}
