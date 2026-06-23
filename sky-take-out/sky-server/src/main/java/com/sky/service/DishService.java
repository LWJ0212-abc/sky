package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * @param dishDTO:
      * @return void
     * @author lwj
     * @description 新增菜品及对应的口味
     * @date 2026/6/20 21:49
     */
   public void saveWithFlavor(DishDTO dishDTO);

   public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO getById(Long id);

    void update(DishDTO dishDTO);

    List<Dish> list(Long categoryid);
}
