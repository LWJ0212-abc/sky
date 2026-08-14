package com.sky.service.impl;

import com.alibaba.druid.mock.MockCallableStatement;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.properties.AlipayProperties;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service.impl
 * @className: OrderServiceImpl
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/24 14:17
 * @version: 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        //异常情况的处理（收货地址为空、超出配送范围、购物车为空）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long UserId = BaseContext.getCurrentId();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(UserId);
        //查询当前用户的购物车
        List<ShoppingCart> shoppingCartList=shoppingCartMapper.list(shoppingCart);
        if (shoppingCartList==null || shoppingCartList.size()==0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        //构造订单数据
        Orders order=new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,order);

        order.setPhone(addressBook.getPhone());
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        order.setConsignee(addressBook.getConsignee());
        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setUserId(UserId);
        order.setStatus(Orders.PENDING_PAYMENT);
        order.setPayStatus(Orders.UN_PAID);
        order.setOrderTime(LocalDateTime.now());

        //插入数据
        orderMapper.insert(order);

        //构造明细数据
        List<OrderDetail> orderDetailList=new ArrayList<>();
        for(ShoppingCart cart:shoppingCartList){
            OrderDetail orderDetail=new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetailList.add(orderDetail);
        }

        //像明细表插入n条数据
        orderDetailMapper.insertBatch(orderDetailList);

        //封装返回结果
        OrderSubmitVO orderSubmitVO= OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime())
                .build();
        return orderSubmitVO;
    }

    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 1. 当前用户 ID
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        // 2. 根据订单号查询数据库中的订单（假设你的订单 Mapper 是 orderMapper）
        Orders orders = orderMapper.getByNumber(ordersPaymentDTO.getOrderNumber());

        // 校验 1：订单是否存在以及是否属于当前用户
        if (orders == null) {
            throw new Exception("订单不存在");
        }
        if (!orders.getUserId().equals(userId)) {
            throw new Exception("无权操作他人订单");
        }

        // 校验 2：关键状态拦截！如果订单已经支付过了，直接报错或抛异常
        // 假设 PayStatus: 0-未支付，1-已支付；OrderStatus: 1-待付款
        if (orders.getPayStatus() == Orders.PAID || orders.getStatus() != Orders.PENDING_PAYMENT) {
            throw new Exception("订单状态异常，已支付或已取消，无需重复支付");
        }

        OrderPaymentVO orderPaymentVO = new OrderPaymentVO();

        if (ordersPaymentDTO.getPayMethod() == 2) { // 支付宝扫码支付
            AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();

            log.info("发送 notify 的地址为：{}", alipayProperties.getNotifyUrl());
            alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());

            JSONObject bizContent = new JSONObject();
            // 建议从数据库的 orders 对象里获取订单号和金额，而不是完全信任前端传进来的 DTO
            bizContent.put("out_trade_no", orders.getNumber());
            // 实际开发中用 orders.getAmount().toString()，沙箱测试可写死 "0.01"
            bizContent.put("total_amount", "0.01");
            bizContent.put("subject", "外卖订单 - " + orders.getNumber());
            bizContent.put("timeout_express", "10m");

            alipayRequest.setBizContent(bizContent.toString());

            try {
                AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest);

                if (response.isSuccess()) {
                    String qrCodeUrl = response.getQrCode();
                    log.info("支付宝预下单成功，二维码URL: {}", qrCodeUrl);

                    orderPaymentVO.setQrCodeUrl(qrCodeUrl);
                } else {
                    log.error("支付宝扫码预下单失败 - code: {}, subMsg: {}", response.getSubCode(), response.getSubMsg());
                    throw new Exception("创建支付订单失败: " + response.getSubMsg());
                }
            } catch (AlipayApiException e) {
                log.error("调用支付宝 SDK 发生异常", e);
                throw new Exception("支付宝支付服务异常");
            }
        }
        return orderPaymentVO;
    }

    @Override
    public void payNotify(HttpServletRequest request) throws AlipayApiException {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            log.info("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayProperties.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                log.info("交易名称: {}", params.get("subject"));
                log.info("交易状态: {}", params.get("trade_status"));
                log.info("支付宝交易凭证号: {}", params.get("trade_no"));
                log.info("商户订单号: {}", params.get("out_trade_no"));
                log.info("交易金额: {}", params.get("total_amount"));
                log.info("买家在支付宝唯一id: {}", params.get("buyer_id"));
                log.info("买家付款时间: {}", params.get("gmt_payment"));
                log.info("买家付款金额: {}", params.get("buyer_pay_amount"));

                // 更新订单未已支付
                Orders orders = orderMapper.getByNumber(tradeNo);
                if (orders != null && orders.getPayStatus() ==Orders.UN_PAID) {
                    // 2. 更新订单状态为已支付
                    orders.setPayStatus(Orders.PAID);
                    orders.setStatus(Orders.TO_BE_CONFIRMED); // 比如状态变为待接单/待发货
                    // 3. 记录支付宝交易流水号和支付时间
                    orders.setCheckoutTime(LocalDateTime.now());
                    // 4. 更新数据库
                    orderMapper.update(orders);
                }
            }
        }
    }

    @Override
    public boolean queryPayStatus(String orderNumber) {
        Orders order=new   Orders();
        order.setUserId(BaseContext.getCurrentId());
        order.setNumber(orderNumber);
        Orders orders=orderMapper.getByNumber(orderNumber);
        log.info("搜索到的order:{}",orders);
        if(Objects.equals(orders.getPayStatus(), Orders.PAID)){
            return true;
        }
        return false;
    }
}
