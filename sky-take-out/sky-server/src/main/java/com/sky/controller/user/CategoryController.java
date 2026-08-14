<<<<<<< HEAD
package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: CategoryController
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/28 00:46
 * @version: 1.0
 */
@RestController("UserCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Api("用户分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("查询分类")
    public Result<List<Category>> getCategoryList(Integer type){
        log.info("C端查询分类：{}",type);
        List<Category> list=categoryService.list(type);
        return Result.success(list);
    }
}
=======
package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: CategoryController
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/28 00:46
 * @version: 1.0
 */
@RestController("UserCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Api("用户分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("查询分类")
    public Result<List<Category>> getCategoryList(Integer type){
        log.info("C端查询分类：{}",type);
        List<Category> list=categoryService.list(type);
        return Result.success(list);
    }
}
>>>>>>> b9c976c421116e53dc22a6e710dbb89ad3856829
