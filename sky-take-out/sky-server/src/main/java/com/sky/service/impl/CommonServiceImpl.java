package com.sky.service.impl;

import com.sky.properties.RustFSProperties;
import com.sky.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URLConnection;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service.impl
 * @className: CommonServiceImpl
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/20 21:13
 * @version: 1.0
 */
@Service
@Slf4j
public class CommonServiceImpl  implements CommonService {
    //构造注入
    private final S3Client s3Client;
    private final RustFSProperties rustFSProperties;

    public CommonServiceImpl(S3Client s3Client, RustFSProperties rustFSProperties) {
        this.s3Client = s3Client;
        this.rustFSProperties = rustFSProperties;
    }

    /**
     * @param bytes:
    	 * @param objectName:
      * @return String
     * @author lwj
     * @description 实现文件上传功能
     * @date 2026/6/20 21:18
     */
    @Override
    public String upload(byte[] bytes, String objectName) {
        try{
            // 1. 根据文件名后缀动态猜测 Content-Type (如果是图片则可以让浏览器直接预览)
            String contentType = URLConnection.guessContentTypeFromName(objectName);
            if (contentType == null) {
                contentType = "application/octet-stream"; // 兜底类型（二进制流）
            }
            PutObjectRequest putObjectRequest=PutObjectRequest.builder()
                    .bucket(rustFSProperties.getBucketName())
                    .key(objectName)
                    .contentType(contentType)
                    .build();
            //调用s3Client执行上传操作
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
            log.info("文件上传至RustFS,Object key：{}", objectName);

            return String.format("%s/%s/%s",rustFSProperties.getEndpoint(),rustFSProperties.getBucketName(), objectName);
        }catch (Exception e){
            log.error("RustFS 文件上传过程中发生异常, 文件名: {}", objectName, e);
            throw new RuntimeException(e);
        }
    }
}
