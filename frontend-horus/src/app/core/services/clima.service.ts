import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClimaDTO } from '../modelos/clima-dto';

@Injectable({
  providedIn: 'root'
})
export class ClimaService {
  private readonly apiUrl = 'http://localhost:8080/api/clima';

  constructor(private http: HttpClient) {}

  obtenerClima(ciudad: string): Observable<ClimaDTO> {
    return this.http.get<ClimaDTO>(`${this.apiUrl}`, { params: { ciudad } });
  }
}