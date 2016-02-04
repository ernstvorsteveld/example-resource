package nl.vorstdev.example.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ernstvorsteveld on 16/01/16.
 */
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/persons/**"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
//                .globalResponseMessage(RequestMethod.GET,
//                        Lists.newArrayList(new ResponseMessageBuilder()
//                                .code(500)
//                                .message("500 message")
//                                .responseModel(new ModelRef("Error"))
//                                .build()));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Persons REST API",
                "The Persons API specification.",
                "1.0-SNAPSHOT",
                "Terms of service",
                "ernst.vorsteveld@gmail.com",
                "License of API",
                "/persons");
        return apiInfo;

    }
}
