package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: ShopController
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/27 22:54
 * @version: 1.0
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "商家相关接口")
public class ShopController {
    public static final String Key="SHOP_STATUS";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("调整运营状态")
    public Result<String> setStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态为：{}",status==1?"营业":"休息");
        redisTemplate.opsForValue().set(Key,status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("管理端营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(Key);
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
