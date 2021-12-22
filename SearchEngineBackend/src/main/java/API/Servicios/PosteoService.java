package API.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import API.Repositorios.PosteoRepository;
import API.Entidades.*;
import java.util.*;

/**
 *
 * @author ACOSTAFX97
 */
@Service
public class PosteoService {
    
    private final PosteoRepository pr;
    
    @Autowired
    public PosteoService(PosteoRepository pr) {
        this.pr = pr;
    }
    
    public void generarPosteos(HashMap<String, Palabra> tablaArchivo, String archivo, int batchSize) {
        List<Posteo> listaPosteos = new ArrayList<>();
        
        tablaArchivo.values().forEach(p -> {
            String palabra = p.getPalabra();
            int frecuencia = p.getFrecuencia();
            
            listaPosteos.add(new Posteo(archivo, frecuencia, palabra));
        });
        
        this.persistirPosteos(listaPosteos, batchSize);
    }
    
    public void persistirPosteos(List<Posteo> listaPosteos, int batchSize) {
        this.pr.batchInsert(listaPosteos, batchSize);
    }
    
}
