package com.sky.service;

import com.alipay.api.AlipayApiException;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service
 * @className: OrderService
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/24 14:17
 * @version: 1.0
 */
public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void payNotify(HttpServletRequest request) throws AlipayApiException;

    boolean queryPayStatus(String  id);
}
