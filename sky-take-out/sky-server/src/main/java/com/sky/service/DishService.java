package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {

    /**
     * @param dishDTO:
      * @return void
     * @author lwj
     * @description 新增菜品及对应的口味
     * @date 2026/6/20 21:49
     */
   public void saveWithFlavor(DishDTO dishDTO);
}
