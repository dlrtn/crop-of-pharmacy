package lalalabs.pharmacy_crop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("약방의 작물 백엔드 애플리케이션 서버")
                .description("약방의 작물 백엔드 애플리케이션 서버 API 명세서")
                .version("0.0.1");
    }
}
