package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;


@ConfigurationProperties(prefix = "sky.rustfs")
@Data
public class RustFSProperties {

    private URI endpoint= URI.create("http://localhost:9001");
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;

}
