package API.Servicios;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import API.Repositorios.PosteoRepository;
import API.Entidades.Palabra;
import java.io.File;
import java.util.*;

/**
 *
 * @author ACOSTAFX97
 */
@Service
public class IndexadorService {
    
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;
    
    private final File UBDD = new File("src/main/resources/static/UBDD");
    
    private final ArchivoService as;
    private final BuscadorService bs;
    private final PosteoService ps;
    private final VocabularioService vs;
    private final PosteoRepository pr;
    
    @Autowired
    public IndexadorService(ArchivoService as, BuscadorService bs, PosteoService ps, VocabularioService vs, PosteoRepository pr) {
        this.as = as;
        this.bs = bs;
        this.ps = ps;
        this.vs = vs;
        this.pr = pr;
    }
    
    public boolean indexarBaseDocumental(String BDD) {
        boolean indexado = false;
        
        String[] listaArchivos = new File(BDD).list();
        
        int contador = 0;
        for (String archivo : listaArchivos) {
            contador++;
            
            if (!this.pr.existsByNombreArchivo(archivo)) {
                indexado = true;
                
                System.out.println("--> ARCHIVO " + contador + "/" + listaArchivos.length + ": " + archivo + " [INDEXANDO]");
                
                HashMap<String, Palabra> tablaArchivo = this.as.generarTablaArchivo(BDD + "/" + archivo);
                this.vs.generarVocabulario(tablaArchivo, this.batchSize);
                this.ps.generarPosteos(tablaArchivo, archivo, this.batchSize);
            } else {
                System.out.println("--> ARCHIVO " + contador + "/" + listaArchivos.length + ": " + archivo + " [EXISTENTE]");
            }
        }
        
        return indexado;
    }
    
    public boolean indexarBaseDocumentalFormulario(MultipartFile archivo) {
        boolean resultado = false;
        
        if (this.as.guardarArchivo(archivo)) {
            resultado = this.indexarBaseDocumental(this.UBDD.getAbsolutePath());
            this.bs.actualizarVocabulario();
            this.as.borrarArchivos();
        }
        
        return resultado;
    }
    
}
