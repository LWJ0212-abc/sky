package com.sky.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.sky.properties.AlipayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: sky-take-out
 * @package: com.sky.config
 * @className: AlipayConfigure
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/24 17:43
 * @version: 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class AlipayConfiguration {

    @Bean
    public AlipayClient alipayClient(AlipayProperties alipayProperties) {
       log.info("加载alipay支付客户端");
       log.info( "ali的支付网关：{}",alipayProperties.getGatewayUrl());
        return new DefaultAlipayClient(
                alipayProperties.getGatewayUrl(),
                alipayProperties.getAppid(),
                alipayProperties.getMerchantPrivateKey(),
                alipayProperties.getFormat(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType()
        );
    }
}
