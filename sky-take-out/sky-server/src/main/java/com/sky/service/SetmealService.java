<<<<<<< HEAD
package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void saveWithDish(SetmealDTO setmealDto);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteBatch(List<Long> ids);

    SetmealVO getById(Long id);

    void upate(SetmealDTO setmealDto);

    void startOrStop(Integer status, Long id);

    /**
     * @param setmeal:
      * @return List<Setmeal>
     * @author lwj
     * @description  根据分类id查询所有setmeal
     * @date 2026/6/28 01:04
     */
    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemById(Long id);
}
=======
package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void saveWithDish(SetmealDTO setmealDto);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteBatch(List<Long> ids);

    SetmealVO getById(Long id);

    void upate(SetmealDTO setmealDto);

    void startOrStop(Integer status, Long id);

    /**
     * @param setmeal:
      * @return List<Setmeal>
     * @author lwj
     * @description  根据分类id查询所有setmeal
     * @date 2026/6/28 01:04
     */
    List<Setmeal> list(Setmeal setmeal);
}
>>>>>>> b9c976c421116e53dc22a6e710dbb89ad3856829
