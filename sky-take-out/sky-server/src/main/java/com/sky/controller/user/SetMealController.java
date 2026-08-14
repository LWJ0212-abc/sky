package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: SetMealController
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/28 00:56
 * @version: 1.0
 */
@RestController("UserSetMealController")
@RequestMapping("/user/setmeal")
@Api("用户套餐查询")
@Slf4j
public class SetMealController {

    @Autowired
    private SetmealService setmealService;

    @Cacheable(cacheNames= "setmealCache",key="#categoryID")
    @GetMapping("/list")
    @ApiOperation("用户根据分类id查询套餐")
    public Result<List<Setmeal>>getSetMealList(Long categoryId){
        log.info("根据类别id查询套餐：{}",categoryId);
        Setmeal setmeal=new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list=setmealService.list(setmeal);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品列表")
    public Result<List<DishItemVO>>dishList(@PathVariable Long id){
        log.info("C端传来的套餐id:{}",id);
        List<DishItemVO> list=setmealService.getDishItemById(id);
        return Result.success(list);
    }
}
