import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ShoppingItemDTO } from '../models/shopping-item.dto';

@Injectable({
  providedIn: 'root'
})
export class ShoppingItemService {
  private readonly apiUrl = 'http://localhost:8080/api/shopping-items';

  constructor(private http: HttpClient) {}

  obtenerArticulos(): Observable<ShoppingItemDTO[]> {
    return this.http.get<ShoppingItemDTO[]>(this.apiUrl);
  }

  obtenerArticuloPorId(id: number): Observable<ShoppingItemDTO> {
    return this.http.get<ShoppingItemDTO>(`${this.apiUrl}/${id}`);
  }

  crearArticulo(dto: ShoppingItemDTO): Observable<ShoppingItemDTO> {
    return this.http.post<ShoppingItemDTO>(this.apiUrl, dto);
  }

  actualizarArticulo(id: number, dto: ShoppingItemDTO): Observable<ShoppingItemDTO> {
    return this.http.put<ShoppingItemDTO>(`${this.apiUrl}/${id}`, dto);
  }

  eliminarArticulo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  agregarArticuloATarea(taskId: number, dto: ShoppingItemDTO): Observable<ShoppingItemDTO> {
    return this.http.post<ShoppingItemDTO>(`/api/tasks/${taskId}/items`, dto);
  }

  obtenerArticulosDeTarea(taskId: number): Observable<ShoppingItemDTO[]> {
    return this.http.get<ShoppingItemDTO[]>(`/api/tasks/${taskId}/items`);
  }

  actualizarArticuloEnTarea(taskId: number, itemId: Long, dto: ShoppingItemDTO): Observable<ShoppingItemDTO> {
    return this.http.put<ShoppingItemDTO>(`/api/tasks/${taskId}/items/${itemId}`, dto);
  }

  eliminarArticuloDeTarea(taskId: Long, itemId: Long): Observable<void> {
    return this.http.delete<void>(`/api/tasks/${taskId}/items/${itemId}`);
  }
}