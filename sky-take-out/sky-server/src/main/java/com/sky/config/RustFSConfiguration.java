package com.sky.config;

import com.sky.properties.RustFSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;


/**
 * @projectName: sky-take-out
 * @package: com.sky.config
 * @className: RustFSConfiguration
 * @author: lwj
 * @description: 生成rustfs工具类
 * @date: 2026/6/20 20:35
 * @version: 1.0
 */
//在 Maven 多模块项目（如苍穹外卖架构）中，父工程的 <dependencyManagement> 标签只是一个“版本声明清单”，它并不会在任何子模块中真正引入依赖。它的作用是统一管理版本号（这里你定义了 ${s3} 为 2.25.27）。
//你的配置类 RustFSConfiguration 写在子模块（通常是 sky-common 或 sky-server）里，由于子模块没有去真正继承并引入这个依赖，所以编译器才会报“找不到 S3Client 符号”。
@Configuration
@EnableConfigurationProperties(RustFSProperties.class)
@Slf4j
public class RustFSConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public S3Client s3Client(RustFSProperties properties){
        log.info("正在初始化RustFS 标准 s3 客户端....");
        S3Client s3 = S3Client.builder()
                .endpointOverride(properties.getEndpoint()) // RustFS 地址
                .region(Region.of(properties.getRegion())) // 可写死，RustFS 不校验 region
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())
                        )
                )
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
        return s3;
    }
}
