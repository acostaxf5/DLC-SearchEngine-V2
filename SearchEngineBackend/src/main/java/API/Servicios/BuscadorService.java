package API.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import API.Repositorios.VocabularioRepository;
import javax.annotation.PostConstruct;
import API.Entidades.*;
import java.util.*;

/**
 *
 * @author ACOSTAFX97
 */
@Service
public class BuscadorService {
    
    private HashMap<String, Vocabulario> vocabulario;
    
    private final ConsultaService cs;
    private final VocabularioRepository vr;
    
    @Autowired
    public BuscadorService(ConsultaService cs, VocabularioRepository vr) {
        this.cs = cs;
        this.vr = vr;
    }
    
    @PostConstruct
    public void init() {
        this.vocabulario = this.vr.findAll();
    }
    
    public void actualizarVocabulario() {
        this.vocabulario = this.vr.findAll();
    }
    
    public List<Archivo> realizarConsulta(String consulta, int limite) {
        List<String> consultaLimpia = this.cs.limpiarConsulta(consulta);
        List<Vocabulario> rankingConsulta = this.cs.rankingConsulta(consultaLimpia, this.vocabulario);
        
        return this.cs.obtenerArchivosConsulta(rankingConsulta, (limite < 0) ? 0 : limite);
    }

}
