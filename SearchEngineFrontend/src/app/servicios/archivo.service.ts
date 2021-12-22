import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArchivoService {

  baseUrl: string = environment.conexionWebApi;

  constructor(private http: HttpClient) {  }

  obtenerArchivo(archivo: string): Observable<Blob> {
    return this.http.get(this.baseUrl + "archivos/obtener/" + archivo, {responseType: 'blob'});
  }

}
