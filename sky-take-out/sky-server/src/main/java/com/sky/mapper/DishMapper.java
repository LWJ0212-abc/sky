package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    /**根据category_id来获取相关菜肴数目
     * @param id:
      * @return Integer
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:18
     */
    @Select("select count(id) from dish where category_id=#{id}")
    Integer countByCategoryId(Long id);

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);
}
