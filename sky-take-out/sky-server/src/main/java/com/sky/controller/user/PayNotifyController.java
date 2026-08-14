package com.sky.controller.user;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.sky.entity.Orders;
import com.sky.result.Result;
import com.sky.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: PayNotifyController
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/27 19:45
 * @version: 1.0
 */
@RestController
@RequestMapping("/notify")
@Api(tags = "支付回调")
@Slf4j
public class PayNotifyController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ApiOperation("支付回调")
    public Result pyaNotify(HttpServletRequest request) throws AlipayApiException {
          log.info("执行支付宝回调");
          orderService.payNotify(request);
          return Result.success();
    }
}
