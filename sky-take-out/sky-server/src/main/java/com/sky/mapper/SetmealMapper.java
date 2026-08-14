<<<<<<< HEAD
package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**通过category_id来获取setmeal表下与类别相关的套餐数目
     * @param id:
      * @return Integer
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:20
     */
    @Select("select count(id) from setmeal where category_id=#{id}")
    Integer countByCategoryId(Long id);

//
//    useGeneratedKeys="true" — 告诉 MyBatis 使用数据库生成的主键
//            keyProperty="id" — 将生成的主键回写到实体对象的 id 属性
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Select("select * from setmeal where id=#{setmealId}")
    Setmeal getById(Long setmealId);

    @Delete("delete from setmeal where id=#{setmealId}")
    void deleteById(Long setmealId);


    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    List<Setmeal> list(Setmeal setmeal);
}
=======
package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**通过category_id来获取setmeal表下与类别相关的套餐数目
     * @param id:
      * @return Integer
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:20
     */
    @Select("select count(id) from setmeal where category_id=#{id}")
    Integer countByCategoryId(Long id);

//
//    useGeneratedKeys="true" — 告诉 MyBatis 使用数据库生成的主键
//            keyProperty="id" — 将生成的主键回写到实体对象的 id 属性
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Select("select * from setmeal where id=#{setmealId}")
    Setmeal getById(Long setmealId);

    @Delete("delete from setmeal where id=#{setmealId}")
    void deleteById(Long setmealId);


    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    List<Setmeal> list(Setmeal setmeal);
}
>>>>>>> b9c976c421116e53dc22a6e710dbb89ad3856829
