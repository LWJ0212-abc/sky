package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: DishController
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/28 00:52
 * @version: 1.0
 */
@RestController("UserDishController")
@Api("用户菜品相关接口")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @GetMapping("/list")
    @ApiOperation("用户菜品列表")
    public Result<List<DishVO>> getDishList(Long categoryId){
            log.info("用户菜品列表：{}",categoryId);
            String key="dish_"+categoryId;
            //获取dish
            List<DishVO> list=null;
            list= (List<DishVO>) redisTemplate.opsForValue().get(key);
            if(list!=null&&list.size()>0){
                return Result.success(list);
            }
            list=dishService.listWithFlavor(categoryId);
            redisTemplate.opsForValue().set(key,list);
            return Result.success(list);
    }
}
