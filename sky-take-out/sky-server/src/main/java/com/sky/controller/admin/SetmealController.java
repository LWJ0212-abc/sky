package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: SetmealController
 * @author: lwj
 * @date: 2026/6/23 22:44
 * @version: 1.0
 */
@Slf4j
@RestController
@Api(tags = "套餐相关接口")
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDto.categoryId")
    @PostMapping()
    @ApiOperation("新增套餐")
    public Result<String> saveSetmeal(@RequestBody SetmealDTO setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult>pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询：{}",setmealPageQueryDTO);
        PageResult pageResult=setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }


    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    @DeleteMapping
    @ApiOperation("根据id删除套餐")
    public Result<String> deleteSetmealById(@RequestParam List<Long> ids){
        log.info("要删除的套餐id:{}",ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("套餐id:{}",id);
        SetmealVO setmealVO=setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> updateSetmeal(@RequestBody SetmealDTO setmealDto){
        setmealService.upate(setmealDto);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改状态")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<String> updateSetmealStatus(@PathVariable Integer status,Long id){
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}
