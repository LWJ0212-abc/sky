<<<<<<< HEAD
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

    /**
     * @param type:
      * @return List<Category>
     * @author lwj
     * @description 查询分类，根据type查询
     * @date 2026/6/28 00:51
     */
    List<Category> list(Integer type);
}
=======
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

    /**
     * @param type:
      * @return List<Category>
     * @author lwj
     * @description 查询分类，根据type查询
     * @date 2026/6/28 00:51
     */
    List<Category> list(Integer type);
}
>>>>>>> b9c976c421116e53dc22a6e710dbb89ad3856829
