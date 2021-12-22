package API.Entidades;

import lombok.Data;

/**
 *
 * @author ACOSTAFX97
 */
@Data
public class Palabra {
    
    private String palabra;
    private int frecuencia;
    
    public Palabra(String palabra, int frecuencia) {
        this.palabra = palabra;
        this.frecuencia = frecuencia;
    }
    
    public void incrementarFrecuencia() {
        this.frecuencia++;
    }
    
}
