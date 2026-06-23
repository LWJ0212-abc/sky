package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: DishController
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/20 21:44
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }


    /**
     * @param
      * @return Result<PageResult>
     * @author lwj
     * @description 菜品分页查询
     * @date 2026/6/20 22:35
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询参数：{}", dishPageQueryDTO);
        PageResult page=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(page);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除:{}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品：{}",id);
        DishVO dishvo=dishService.getById(id);
        return Result.success(dishvo);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result<String>updateById(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品信息{}",dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("分类的id：{}",categoryId);
        List<Dish> list=dishService.list(categoryId);
        return Result.success(list);
    }

}
