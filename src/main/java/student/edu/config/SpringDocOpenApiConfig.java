package student.edu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .title("REST API - Movies & Series Watched")
                .description("API para gestão de filmes e series.")
                .contact(new Contact().name("Lucas Xavier").email("lucas.lima.xaviier@gmail.com"))
                .license(new License().name("Apache 2.0").url("apache.org/licenses.LICENSE-2.0"))
                .version("v1"));
    }
}
