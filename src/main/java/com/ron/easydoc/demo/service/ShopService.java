package com.ron.easydoc.demo.service;

import com.ron.easydoc.annotation.DocApi;
import com.ron.easydoc.annotation.DocMethod;
import com.ron.easydoc.demo.domain.Shop;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 商店服务接口
 * @author ron
 * @date 2021年05月13日 14:53
 */
@DocApi
public interface ShopService {

    /**
     * 获取商店
     * @param shopName 商店名称(模糊查询)
     * @param shopNum  商店数量
     * @return 商品实体
     */
    @DocMethod
    public Shop getShop(String shopName, int shopNum);


    //参数返回值无法通过智能查询找到时，可以通过注释指定 #tyep:...#
    /**
     * 创建商店
     * @param shop
     * @return #type:com.ron.easydoc.demo.domain.Shop#
     */
    @DocMethod
    public Shop createShop(Shop shop);

    /**
     * 根据商店id获取name
     * @param id 商店id
     * @return 商店name
     */
    @DocMethod
    public String getNameById(long id);

    /**
     * 获取商店数量
     * @return 商店数量
     */
    @DocMethod
    public int getShopNum();

    /**
     * 商店是否存在
     * @param shopName 商店名称
     * @return 是否存在
     */
    @DocMethod
    public Boolean getShopNum(String shopName);

    /**
     * 删除商店
     * @param id 商店id
     * @return 是否成功
     */
    public Shop deleteShop(@PathVariable(name="id") long id);
}
