package com.example.klasha.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Klasha API", version = "v1",
        contact = @Contact(url = "https://github.com", name = "Abdulraheem Sherif Adavuruku", email = "aabdulraheemsherif@gmail.com"),
        description = "Klasha Technical Interview API Documentation"))
//@SecurityScheme(
//        name = "bearerAuth",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
public class ApiDocConfig { }
