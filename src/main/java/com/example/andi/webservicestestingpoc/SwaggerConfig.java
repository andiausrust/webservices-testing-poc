package com.example.andi.webservicestestingpoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Contact contact = new Contact(
      "Andi Mayer",
      "http://andiausrust",
      "test@test.com"
    );

    // custom properties e.g. x-logger
    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "POC RestFul Web Service documentation",
            "This page documents a POC RestFul Web Services endpoint",
            "1.0",
            "http://andiausrust",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENXE-2.0",
            vendorExtensions);

    @Bean
    public Docket apiDocket() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPS")))
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.andi.webservicestestingpoc"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }
}
