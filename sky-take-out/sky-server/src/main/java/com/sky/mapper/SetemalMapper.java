package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetemalMapper {

    /**通过category_id来获取setmeal表下与类别相关的套餐数目
     * @param id:
      * @return Integer
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:20
     */
    @Select("select count(id) from setmeal where category_id=#{id}")
    Integer countByCategoryId(Long id);
}
