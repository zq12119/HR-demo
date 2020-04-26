package com.springboot.vhrend.config;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@Data
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("消息管理API")
                .directModelSubstitute(LocalDate.class,String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true).select()
                .apis(RequestHandlerSelectors.basePackage("com.springboot.vhrend.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();

    }
    //配置页面展示的基本信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("消息管理")
                .description("消息管理API文档")
                .termsOfServiceUrl("http://localhost:8888")
                .contact(new Contact("niit","http://www.niit.com","admin@niit.com"))
                .version("1.0")
                .build();
    }
}
