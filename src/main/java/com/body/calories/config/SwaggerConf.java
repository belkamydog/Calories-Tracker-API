package com.body.calories.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "Calories count Api",
                description = "API for count calories",
                version = "1.0.0",
                contact = @Contact(
                        name = "Efimov Artyom",
                        email = "belkamydog@yandex.ru"
                )
        )
)

@Configuration
public class SwaggerConf {

}
