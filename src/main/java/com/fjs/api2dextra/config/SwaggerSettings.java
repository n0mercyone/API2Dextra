package com.fjs.api2dextra.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerSettings {

    @Bean
    public Docket api2dextra() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.fjs.api2dextra"))
                .paths(PathSelectors.regex("/api.*")).build().apiInfo(metaInfo());

    }

    public ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo("Happy Potter • API REST",
                "API REST • CRUD Harry Potter characters for api2dextra", "1.0", "Terms Of Service",
                new Contact("Frank jr", "https://foxmanager.com.br/", "frank.apk@gmail.com"),
                "Apache Licente Verion 2.0", "https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
        return apiInfo;
    }

}
