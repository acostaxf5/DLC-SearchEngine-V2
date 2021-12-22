package API.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import API.Repositorios.VocabularioRepository;
import API.Entidades.*;
import java.util.*;

/**
 *
 * @author ACOSTAFX97
 */
@Service
public class VocabularioService {
    
    private final VocabularioRepository vr;
    
    @Autowired
    public VocabularioService(VocabularioRepository vr) {
        this.vr = vr;
    }
    
    public void generarVocabulario(HashMap<String, Palabra> tablaArchivo, int batchSize) {
        HashMap<String, Vocabulario> tablaVocabulario = this.vr.findAll();
        HashMap<String, Vocabulario> tablaAuxiliarVocabulario = new HashMap<>();
        
        tablaArchivo.values().forEach(p -> {
            String palabra = p.getPalabra();
            int frecuencia = p.getFrecuencia();
            
            Vocabulario vocabularioExistente = tablaVocabulario.get(palabra);
            if (vocabularioExistente == null) {
                vocabularioExistente = new Vocabulario(palabra, 1, frecuencia);
            } else {
                vocabularioExistente.incrementarCantidadDocumentos();
                if (frecuencia > vocabularioExistente.getFrecuenciaMaxima()) {
                    vocabularioExistente.setFrecuenciaMaxima(frecuencia);
                }
            }
            
            tablaAuxiliarVocabulario.put(palabra, vocabularioExistente);
        });
        
        this.persistirVocabulario(tablaAuxiliarVocabulario, batchSize);
    }
    
    public void persistirVocabulario(HashMap<String, Vocabulario> tablaVocabulario, int batchSize) {
        List<Vocabulario> insert = new ArrayList<>();
        List<Vocabulario> update = new ArrayList<>();
        
        tablaVocabulario.values().forEach(v -> {
            if (v.getId() == 0) {
                insert.add(v);
            } else {
                update.add(v);
            }
        });
        
        this.vr.batchInsert(insert, batchSize);
        this.vr.batchUpdate(update, batchSize);
    }
    
}
