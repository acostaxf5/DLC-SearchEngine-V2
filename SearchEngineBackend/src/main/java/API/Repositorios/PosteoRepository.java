package API.Repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import API.Entidades.Posteo;
import java.util.List;

/**
 *
 * @author ACOSTAFX97
 */
@Repository
public class PosteoRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public PosteoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Transactional
    public void batchInsert(List<Posteo> listaPosteos, int batchSize) {
        String SQL = "INSERT INTO posteos (nombre_archivo, frecuencia, palabra_vocabulario) VALUES (?, ?, ?)";
        
        this.jdbcTemplate.batchUpdate(SQL, listaPosteos, batchSize, (ps, posteo) -> {
            ps.setString(1, posteo.getNombreArchivo());
            ps.setInt(2, posteo.getFrecuencia());
            ps.setString(3, posteo.getPalabraVocabulario());
        });
    }
    
    public boolean existsByNombreArchivo(String nombreArchivo) {
        String SQL = "SELECT count(*) FROM posteos WHERE nombre_archivo = ?";
        
        return this.jdbcTemplate.queryForObject(SQL, Integer.class, nombreArchivo) > 0;
    }
    
    public List<Posteo> findByPalabraLimit(String palabra, int limite) {
        String SQL = "SELECT * FROM posteos WHERE palabra_vocabulario = ? ORDER BY frecuencia DESC LIMIT ?";
        
        return this.jdbcTemplate.query(SQL, (rs, row) -> {
            Posteo posteo = new Posteo();
            posteo.setId(rs.getInt("id_posteo"));
            posteo.setNombreArchivo(rs.getString("nombre_archivo"));
            posteo.setFrecuencia(rs.getInt("frecuencia"));
            posteo.setPalabraVocabulario(rs.getString("palabra_vocabulario"));
            
            return posteo;
        }, palabra, limite);
    }
    
}
