package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: OrderController
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/24 14:14
 * @version: 1.0
 */
@RestController
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}",ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO=orderService.payment(ordersPaymentDTO);
        log.info("生成预支付的订单：{}",orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    @GetMapping("/queryPayStatus/{id}")
    @ApiOperation("查询支付状态")
    public Result queryPayStatus( @PathVariable String  id) throws Exception {
         log.info("查询订单：{}",id);
         boolean status=orderService.queryPayStatus(id);
         return Result.success(status);
    }
}
