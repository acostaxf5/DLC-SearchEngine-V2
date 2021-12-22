package API.Repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import API.Entidades.Vocabulario;
import java.util.*;

/**
 *
 * @author ACOSTAFX97
 */
@Repository
public class VocabularioRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public VocabularioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Transactional
    public void batchInsert(List<Vocabulario> listaVocabulario, int batchSize) {
        String SQL = "INSERT INTO vocabulario (palabra, cantidad_archivos, frecuencia_maxima) VALUES (?, ?, ?)";
        
        this.jdbcTemplate.batchUpdate(SQL, listaVocabulario, batchSize, ((ps, vocabulario) -> {
            ps.setString(1, vocabulario.getPalabra());
            ps.setInt(2, vocabulario.getCantidadArchivos());
            ps.setInt(3, vocabulario.getFrecuenciaMaxima());
        }));
    }
    
    @Transactional
    public void batchUpdate(List<Vocabulario> listaVocabulario, int batchSize) {
        String SQL = "UPDATE vocabulario SET cantidad_archivos = ?, frecuencia_maxima = ? WHERE id_vocabulario = ?";
        
        this.jdbcTemplate.batchUpdate(SQL, listaVocabulario, batchSize, ((ps, vocabulario) -> {
            ps.setInt(1, vocabulario.getCantidadArchivos());
            ps.setInt(2, vocabulario.getFrecuenciaMaxima());
            ps.setInt(3, vocabulario.getId());
        }));
    }
    
    public HashMap<String, Vocabulario> findAll() {
        String SQL = "SELECT * FROM vocabulario";
        
        List<Vocabulario> resultados = this.jdbcTemplate.query(SQL, (rs, row) -> {
            Vocabulario vocabulario = new Vocabulario();
            vocabulario.setId(rs.getInt("id_vocabulario"));
            vocabulario.setPalabra(rs.getString("palabra"));
            vocabulario.setCantidadArchivos(rs.getInt("cantidad_archivos"));
            vocabulario.setFrecuenciaMaxima(rs.getInt("frecuencia_maxima"));
            
            return vocabulario;
        });
        
        HashMap<String, Vocabulario> tablaVocabulario = new HashMap<>();
        resultados.forEach(v -> tablaVocabulario.put(v.getPalabra(), v));
        
        return tablaVocabulario;
    }
    
}
