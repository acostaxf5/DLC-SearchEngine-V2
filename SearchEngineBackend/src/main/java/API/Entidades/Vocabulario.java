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
@Table(name = "vocabulario")
public class Vocabulario implements Serializable, Comparable<Vocabulario> {
    
    @Id
    @Column(name = "id_vocabulario")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_vocabulario")
    @SequenceGenerator(name = "secuencia_vocabulario", initialValue = 1, allocationSize = 1)
    private int id;
    
    @Column
    private String palabra;
    
    @Column(name = "cantidad_archivos")
    private int cantidadArchivos;
    
    @Column(name = "frecuencia_maxima")
    private int frecuenciaMaxima;
    
    public Vocabulario() {
        
    }
    
    public Vocabulario(String palabra, int cantidadDocumentos, int frecuenciaMaxima) {
        this.palabra = palabra;
        this.cantidadArchivos = cantidadDocumentos;
        this.frecuenciaMaxima = frecuenciaMaxima;
    }
    
    public Vocabulario(int id, String palabra, int cantidadDocumentos, int frecuenciaMaxima) {
        this.id = id;
        this.palabra = palabra;
        this.cantidadArchivos = cantidadDocumentos;
        this.frecuenciaMaxima = frecuenciaMaxima;
    }
        
    @Override
    public String toString() {
        return "Vocabulario [" + this.id + ", " 
                               + this.palabra + ", " 
                               + this.cantidadArchivos + ", " 
                               + this.frecuenciaMaxima + "]";
    }
    
    @Override
    public int compareTo(Vocabulario vocabulario) {
        return this.cantidadArchivos - vocabulario.getCantidadArchivos();
    }
    
    public void incrementarCantidadDocumentos() {
        this.cantidadArchivos++;
    }
    
}
