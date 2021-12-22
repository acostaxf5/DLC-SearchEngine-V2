package API.Entidades;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author ACOSTAFX97
 */
@Data
@Entity
@Table(name = "posteos")
public class Posteo implements Serializable {
    
    @Id
    @Column(name = "id_posteo")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_posteos")
    @SequenceGenerator(name = "secuencia_posteos", initialValue = 1, allocationSize = 1)
    private int id;
    
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    
    @Column
    private int frecuencia;
    
    @Column(name = "palabra_vocabulario")
    private String palabraVocabulario;
    
    public Posteo() {
        
    }
    
    public Posteo(String nombreDocumento, int frecuencia, String palabraVocabulario) {
        this.nombreArchivo = nombreDocumento;
        this.frecuencia = frecuencia;
        this.palabraVocabulario = palabraVocabulario;
    }
    
    public Posteo(int id, String nombreDocumento, int frecuencia, String palabraVocabulario) {
        this.id = id;
        this.nombreArchivo = nombreDocumento;
        this.frecuencia = frecuencia;
        this.palabraVocabulario = palabraVocabulario;
    }
    
    @Override
    public String toString() {
        return "Posteo [" + this.id + ", "
                          + this.nombreArchivo + ", "
                          + this.frecuencia + ", "
                          + this.palabraVocabulario + "]";
    }
    
    public void incrementarFrecuencia() {
        this.frecuencia++;
    }
    
}
