package com.oabs.prescription_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Online Appointment Booking System API",
                version = "1.0",
                description = "This API allows patients to book appointments with doctors."
        )
)
public class OpenApiConfig {
}
