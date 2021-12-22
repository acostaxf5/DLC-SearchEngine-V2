package API.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import API.Repositorios.PosteoRepository;
import javax.annotation.PostConstruct;
import org.springframework.core.io.*;
import API.Entidades.Palabra;
import java.nio.file.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author ACOSTAFX97
 */
@Service
public class ArchivoService {
    
    private final File BDD = new File("src/main/resources/static/BDD/");
    private final File UBDD = new File("src/main/resources/static/UBDD/");
    
    private final PosteoRepository pr;
    
    @Autowired
    public ArchivoService(PosteoRepository pr) {
        this.pr = pr;
    }
    
    @PostConstruct
    public void init() {
        if (!this.UBDD.exists()) {
            this.UBDD.mkdir();
        }
        
        this.borrarArchivos();
    }

    public HashMap<String, Palabra> generarTablaArchivo(String archivo) {
        HashMap<String, Palabra> tablaArchivo = new HashMap<>();

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(archivo)))) {
            scanner.useDelimiter("[^a-zA-Z \\-]|( )|(^\\s*$)|-");

            while (scanner.hasNext()) {
                String palabra = scanner.next().toLowerCase();

                if (palabra.length() > 0) {
                    Palabra palabraExistente = tablaArchivo.get(palabra);
                    
                    if (palabraExistente == null) {
                        tablaArchivo.put(palabra, new Palabra(palabra, 1));
                    } else {
                        palabraExistente.incrementarFrecuencia();
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("--> NO ES POSIBLE LEER EL ARCHIVO: " + ex.getMessage());
        }

        return tablaArchivo;
    }
    
    public void borrarArchivos() {
        File carpetaUBDD = new File(this.UBDD.getAbsolutePath());
        
        if (carpetaUBDD.exists()) {
            for (String archivo : carpetaUBDD.list()) {
                new File(carpetaUBDD.getPath(), archivo).delete();
            }
        }
    }
    
    public boolean guardarArchivo(MultipartFile archivo) {
        String nombreArchivo = archivo.getOriginalFilename();
        
        try {
            if (!this.pr.existsByNombreArchivo(nombreArchivo)) {
                Files.copy(archivo.getInputStream(), new File(this.UBDD.getAbsolutePath(), nombreArchivo).toPath());
                Files.copy(archivo.getInputStream(), new File(this.BDD.getAbsolutePath(), nombreArchivo).toPath());
                
                return true;
            } else {
                System.out.println("--> ARCHIVO 1/1: " + nombreArchivo + " [EXISTENTE]");
            }
        } catch (IOException ex) {
            System.out.println("--> ARCHIVO DUPLICADO: " + ex.getMessage());
        }
        
        return false;
    }
    
    public Resource obtenerArchivo(String nombreArchivo) {
        File archivo = new File("src/main/resources/static/BDD/" + nombreArchivo);
        
        try {
            return new ByteArrayResource(Files.readAllBytes(archivo.toPath()));
        } catch (IOException ex) {
            System.out.println("--> ARCHIVO INEXISTENTE: " + ex.getMessage());
        }
        
        return null;
    }
    
}
