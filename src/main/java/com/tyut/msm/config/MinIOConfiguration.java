package com.tyut.msm.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Liujinzhu
 * @Date: 2022-04-30-14:19
 * @Description:
 */
@Configuration
public class MinIOConfiguration {

    @Value("${endpoint}")
    private String endpoint;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey = "password";

    @Bean
    public MinioClient minioClient() {
        // 创建 MinioClient 客户端
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
