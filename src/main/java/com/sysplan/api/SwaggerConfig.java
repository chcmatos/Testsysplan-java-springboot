package com.sysplan.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docketApiV1() {
        return docket("v1");
    }

    @Bean
    public Docket docketApiV2() {
        return docket("v2");
    }

    private Docket docket(String version) {
        String basePackage = SwaggerConfig.class.getPackage().getName();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-"+version)
                .apiInfo(apiInfo(version))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage + "." + version))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo(String version){
        return new ApiInfoBuilder()
                .title("Sysplan Test API")
                .description("A simple usage example of Java Springboot Web API")
                .contact(new Contact(
                        "Carlos Matos",
                        "https://www.youracclaim.com/users/chcmatos/badges",
                        "chcmatos@gmail.com"))
                .version(version)
                .build();
    }

}
