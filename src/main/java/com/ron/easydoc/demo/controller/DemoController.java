package com.ron.easydoc.demo.controller;

import com.ron.easydoc.demo.domain.Item;
import org.springframework.web.bind.annotation.*;

/**
 * demo服务restful接口
 * @author ron
 * @date 2021年06月02日
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    //参数返回值无法通过智能查询找到时，可以通过注释指定 #tyep:...#
    /**
     * 创建商品
     * @param item
     * @return #type:com.ron.easydoc.demo.domain.Item#
     */
    @PostMapping("createItem")
    public Item createItem(Item item){
        return null;
    }

    /**
     * 删除商品
     * @param code 商品code
     * @return 是否成功
     */
    @GetMapping("deleteItem/{code}")
    public boolean deleteItem(@PathVariable(name="code") long code){
        return false;
    }

    /**
     * 获取商品
     * @param code
     * @param name
     * @return 商品实体
     */
    @RequestMapping(value = "getItem",method = RequestMethod.GET)
    public Item getItem(String code, String name){
        return null;
    }

    //解析将会跳过@Deprecated标识的方法
    /**
     * 获取商品
     * @param code
     * @param name
     * @return 商品实体
     */
    @Deprecated
    @RequestMapping("getItem1")
    public Object getItem1(String code, String name){
        return null;
    }

    //添加#ignore#也会跳过此方法的解析
    /**
     * #ignore#
     * 获取商品
     * @param code
     * @param name
     * @return
     */
    @RequestMapping("getItem2")
    public Object getItem2(String code, String name){
        return null;
    }

}
