package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: CategoryController
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/12 21:38
 * @version: 1.0
 */
@RestController
@Api(tags="分类相关接口")
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**新增分类
     * @param categoryDTO:
      * @return Result
     * @author lwj
     * @description TODO
     * @date 2026/6/12 21:47
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类=>[{}]", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**分类分页查询
     * @param categoryPageQueryDTO:
      * @return Result<PageResult>
     * @author lwj
     * @description TODO
     * @date 2026/6/12 21:58
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> pageCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询=>[{}]", categoryPageQueryDTO);
        PageResult page =categoryService.pageCategory(categoryPageQueryDTO);
        return Result.success(page);
    }

    /**根据id删除
     * @param id:
      * @return Result<String>
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:48
     */
    @DeleteMapping
    @ApiOperation("根据id删除")
    public Result<String> deleteCategory(Long id) {
        log.info("要删除的分类=>[{}]",id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**修改分类
     * @param categoryDTO:
      * @return Result<String>
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:48
     */
    @PutMapping
    @ApiOperation("修改类别")
    public Result<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("需要修改的分类=>[{}]",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**启用或禁用状态
     * @param status:
    	 * @param id:
      * @return Result<String>
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:48
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result<String> switchStatus(@PathVariable Integer status, Long id) {
        log.info("修改分了状态=>[{},{}]",id,status);
        categoryService.switchStatus(id,status);
        return Result.success();
    }
    /** 根据类型查询分类
     * @param type:
      * @return Result<List<Category>>
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:51
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
            log.info("查询的type=>[{}]",type);
            List<Category> res =categoryService.list(type);
            return Result.success(res);
    }
}
