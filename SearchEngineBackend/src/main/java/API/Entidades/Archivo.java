package API.Entidades;

import lombok.Data;

/**
 *
 * @author ACOSTAFX97
 */
@Data
public class Archivo implements Comparable<Archivo> {
    
    private String nombre;
    private double indiceRelevancia;
    
    public Archivo(String nombre) {
        this.nombre = nombre;
    }
    
    public void calcularIndiceRelevancia(int frecuencia, int totalArchivos, int cantidadArchivos) {
        this.indiceRelevancia += frecuencia * (Math.log10(totalArchivos/cantidadArchivos));
    }
    
    @Override
    public int compareTo(Archivo archivo) {
        return (int) (archivo.getIndiceRelevancia() - this.indiceRelevancia);
    }
    
}
