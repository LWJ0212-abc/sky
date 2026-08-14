package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.mapper
 * @className: OrderDetailMapper
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/24 14:19
 * @version: 1.0
 */
@Mapper
public interface  OrderDetailMapper {
    void insertBatch(List<OrderDetail> orderDetailList);
}
