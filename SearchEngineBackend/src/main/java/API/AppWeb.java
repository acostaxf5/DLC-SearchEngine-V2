package API;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "SearchEngine [DLC]", version = "V2"))
public class AppWeb {

    public static void main(String[] args) {
        SpringApplication.run(AppWeb.class, args);
    }
    
}
