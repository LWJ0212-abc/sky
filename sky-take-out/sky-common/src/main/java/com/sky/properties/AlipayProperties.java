package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @projectName: sky-take-out
 * @package: com.sky.properties
 * @className: ZhiFuBaoProperties
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/24 17:11
 * @version: 1.0
 */
@Component
@ConfigurationProperties("sky.alipay")
@Data
public class AlipayProperties {
    private String appid;               //app id

    private String merchantPrivateKey;  //商户私方私钥（PKCS8格式）

    private String   alipayPublicKey;    //支付宝公钥

    private String signType;//签名算法
    private String charset;
    private String  format;         //json

    private String gatewayUrl;   //关键点：沙箱网关地址

    private String notifyUrl;  // 支付成功回调地址
}
