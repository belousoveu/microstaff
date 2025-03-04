package belousov.eu.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("MicroStaff Users API")
                        .description("Проверка HTTP запросов MicroStaff Users API")
                        .version("1.0"));
    }
}