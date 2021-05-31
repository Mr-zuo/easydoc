package com.ron.easydoc.demo.service;

import com.ron.easydoc.demo.domain.Item;

/**
 * demo服务接口
 * @author ron
 * @date 2021年05月13日 14:53
 */
public interface DemoService {

    /**
     * 获取商品
     * @param code 商品编码
     * @param name  商品名称
     * @return 商品实体
     */
    Item getItem(String code, String name);


    //参数返回值无法通过智能查询找到时，可以通过注释指定 #tyep:...#
    /**
     * 创建商品
     * @param item
     * @return #type:com.ron.easydoc.demo.domain.Item#
     */
    Item createItem(Item item);

    /**
     * 根据商品编码获取商品名称
     * @param code 商品编码
     * @return 商品名称
     */
    String getNameByCode(String code);

    /**
     * 获取商品数量
     * @return 商品数量
     */
    int getItemNum();

    /**
     * 根据商品编码判断商品是否存在
     * @param code 商品编码
     * @return 是否存在
     */
    Boolean itemExist(String code);

    /**
     * 删除商品
     * @param code 商品编码
     */
    void deleteItem(String code);
}
