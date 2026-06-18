package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service
 * @className: CategoryService
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/12 21:40
 * @version: 1.0
 */

public interface CategoryService {
    void addCategory(CategoryDTO categoryDTO);

    PageResult pageCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Long id);

    void update(CategoryDTO categoryDTO);

    void switchStatus(Long id,Integer status);

    List<Category> list(Integer type);
}
