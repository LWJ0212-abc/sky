package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service.impl
 * @className: DishServiceImpl
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/20 21:46
 * @version: 1.0
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public void saveWithFlavor(DishDTO dishDTO) {
        //创建dish
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.insert(dish);

        //获取insert语句生成的主键值
        Long dishId=dish.getId();

        List<DishFlavor>flavorList=dishDTO.getFlavors();
        if(flavorList!=null&&flavorList.size()>0){
            flavorList.stream().forEach(flavor->{
                flavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavorList);
        }


    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        //判断当前是否能删除
        for(Long id:ids){
            Dish dish=dishMapper.getById(id);
            if(dish.getStatus()== StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断是否与套餐进行关联
        List<Long>setmealIds= setMealDishMapper.getSetMealIdsByDishIds(ids);
        if(setmealIds!=null&&setmealIds.size()>0){
            throw  new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        for(Long id:ids){
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    @Override
    public DishVO getById(Long id) {
       Dish dish=dishMapper.getById(id);
       List<DishFlavor>dishFlavors=dishFlavorMapper.getByDishId(id);

       DishVO dishVO=new DishVO();
       BeanUtils.copyProperties(dish,dishVO);
       dishVO.setFlavors(dishFlavors);
       return dishVO;
    }

    @Override
    public void update(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.update(dish);

        dishFlavorMapper.deleteByDishId(dish.getId());

        List<DishFlavor>flavorList=dishDTO.getFlavors();
        if(flavorList!=null&&flavorList.size()>0){
            flavorList.stream().forEach(flavor->{
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavorList);
        }
    }
}
