package API.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import API.Entidades.Archivo;
import API.Servicios.*;
import java.util.List;

/**
 *
 * @author ACOSTAFX97
 */
@RestController
@RequestMapping("/search-engine/buscador")
@CrossOrigin(origins = "http://localhost:4200/")
public class BuscadorController {
    
    private final BuscadorService bs;
    
    @Autowired
    public BuscadorController(BuscadorService bs) {
        this.bs = bs;
    }
    
    @GetMapping("/buscar/{consulta}/{limite}")
    public ResponseEntity<List<Archivo>> buscar(@PathVariable String consulta, @PathVariable int limite) {
        return ResponseEntity.ok(this.bs.realizarConsulta(consulta, limite));
    }
    
}
