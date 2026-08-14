package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service
 * @className: ShoppingCartService
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/23 23:19
 * @version: 1.0
 */

public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);


    List<ShoppingCart> showShoppingCart();

    void cleanShoppingCart();

    void subShopingCart(ShoppingCartDTO shoppingCartDTO);
}
