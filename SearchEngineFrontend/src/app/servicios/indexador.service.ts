import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IndexadorService {

  baseUrl: string = environment.conexionWebApi;

  constructor(private http: HttpClient) {  }

  indexar(archivo: File): Observable<boolean> {
    let data: FormData = new FormData();
    data.append("archivo", archivo);

    return this.http.post<boolean>(this.baseUrl + "archivos/indexar", data);
  }

}
