package com.tyut.msm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xh
 * @Date 2022/1/16
 */
@Configuration // 配置类
@EnableSwagger2 // swagger注解
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .build();

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("TYUT留言板API文档")
                .description("本文档描述了TYUT留言板接口定义")
                .version("1.0")
                .contact(new Contact("小航", "xxxx", "1531137510@qq.com"))
                .build();
    }
}
