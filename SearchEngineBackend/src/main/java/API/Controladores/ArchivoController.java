package API.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import API.Servicios.*;

/**
 *
 * @author ACOSTAFX97
 */
@RestController
@RequestMapping("search-engine/archivos")
@CrossOrigin(origins = "http://localhost:4200/")
public class ArchivoController {
    
    private final ArchivoService as;
    private final IndexadorService is;
    
    @Autowired
    public ArchivoController(ArchivoService as, IndexadorService is) {
        this.as = as;
        this.is = is;
    }
    
    @PostMapping("/indexar")
    public ResponseEntity<Boolean> indexar(@RequestParam MultipartFile archivo) {
        return ResponseEntity.ok(this.is.indexarBaseDocumentalFormulario(archivo));
    }
    
    @GetMapping("/obtener/{nombreArchivo}")
    public ResponseEntity<Resource> obtener(@PathVariable String nombreArchivo) {
        Resource recurso = this.as.obtenerArchivo(nombreArchivo);
        
        if (recurso != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(recurso);
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
}
