package API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import API.Servicios.IndexadorService;
import org.springframework.boot.*;
import java.io.File;

/**
 *
 * @author ACOSTAFX97
 */
@ComponentScan(basePackages = "API")
public class AppConsole implements CommandLineRunner {
    
    private final File BDD = new File("src/main/resources/static/BDD/");
    
    private final IndexadorService is;
    
    @Autowired
    public AppConsole(IndexadorService is) {
        this.is = is;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(AppConsole.class, args);
    }
    
    @Override
    public void run(String... args) {
        if (this.is.indexarBaseDocumental(this.BDD.getAbsolutePath())) {
            System.out.println("--> INDEXACIÓN FINALIZADA CON ÉXITO");
        } else {
            System.out.println("--> INDEXACIÓN FALLIDA [NO EXISTEN ARCHIVOS PARA INDEXAR]");
        }
    }
    
}
