package com.sky.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetemalMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service.impl
 * @className: CategoryServiceImpl
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/12 21:41
 * @version: 1.0
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetemalMapper setemalMapper;

    /**添加分类
     * @param categoryDTO:
      * @return void
     * @author lwj
     * @description TODO
     * @date 2026/6/12 21:59
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        //设置分类默认设置
        category.setStatus(StatusConstant.DISABLE);
        categoryMapper.save(category);

    }

    /**分页查询
     * @param categoryPageQueryDTO:
      * @return PageResult
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:00
     */
    @Override
    public PageResult pageCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> pages=categoryMapper.pageCategory(categoryPageQueryDTO);
        PageResult page=new PageResult(
                pages.getTotal(),
                pages.getResult()
        );
        return page;
    }

    /** 根据id删除
     * @param id:
      * @return void
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:10
     */
    @Override
    public void deleteById(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count=dishMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count=setemalMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);

    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }

    /**启用禁用分类
     * @param id:
      * @return void
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:44
     */
    @Override
    public void switchStatus(Long id,Integer status) {
        Category category=Category.builder()
                .id(id)
                .status(status)
                .build();

        categoryMapper.update(category);

    }

    /**跟新类别
     * @param categoryDTO:
      * @return void
     * @author lwj
     * @description TODO
     * @date 2026/6/12 22:43
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        categoryMapper.update(category);
    }
}
