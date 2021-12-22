package API.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import API.Repositorios.PosteoRepository;
import API.Entidades.*;
import java.io.File;
import java.util.*;

/**
 *
 * @author ACOSTAFX97
 */
@Service
public class ConsultaService {
    
    private final File BDD = new File("src/main/resources/static/BDD");
    
    private final PosteoRepository pr;
    
    @Autowired
    public ConsultaService(PosteoRepository pr) {
        this.pr = pr;
    }
    
    public List<String> limpiarConsulta(String consulta) {
        List<String> consultaLimpia = new ArrayList<>();
        
        Arrays.asList(consulta.split(" ")).stream().forEach(p -> {
            StringBuilder palabraLimpia = new StringBuilder();

            for (Character letra : p.toCharArray()) {
                if (Character.isAlphabetic(letra)) {
                    palabraLimpia.append(letra);
                }
            }
            
            consultaLimpia.add(palabraLimpia.toString().toLowerCase());
        });
        
        return consultaLimpia;
    }
    
    public List<Vocabulario> rankingConsulta(List<String> consulta, HashMap<String, Vocabulario> vocabularioBaseDatos) {
        List<Vocabulario> ranking = new ArrayList<>();
        
        consulta.forEach(p -> {
            Vocabulario vocabulario = vocabularioBaseDatos.get(p);
            
            if (vocabulario != null) {
                ranking.add(vocabulario);
            }
        });
        
        Collections.sort(ranking);
        
        return ranking;
    }
    
    public List<Archivo> obtenerArchivosConsulta(List<Vocabulario> ranking, int limite) {
        int archivosBaseDocumental = this.BDD.list().length;
        
        HashMap<String, Archivo> archivosConsulta = new HashMap<>();
        
        ranking.forEach(v -> {
            this.pr.findByPalabraLimit(v.getPalabra(), limite).forEach(p -> {
                int frecuencia = p.getFrecuencia();
                int cantidadArchivos = v.getCantidadArchivos();
                
                Archivo archivoExistente = archivosConsulta.get(p.getNombreArchivo());
                
                if (archivoExistente == null) {
                    archivoExistente = new Archivo(p.getNombreArchivo());
                }

                archivoExistente.calcularIndiceRelevancia(frecuencia, archivosBaseDocumental, cantidadArchivos);

                archivosConsulta.put(archivoExistente.getNombre(), archivoExistente);
            });
        });
        
        List<Archivo> archivos = new ArrayList<>();
        archivosConsulta.values().forEach(a -> archivos.add(a));
        
        Collections.sort(archivos);
        
        List<Archivo> resultados = new ArrayList<>();
        for (int i = 0; i < ((limite > archivos.size()) ? archivos.size() : limite); i++) {
            resultados.add(archivos.get(i));
        }
        
        return resultados;
    }
    
}
