import { Component, OnInit } from '@angular/core';
import { ShoppingItemService } from '../../core/services/shopping-item.service';
import { ShoppingItemDTO } from '../../core/models/shopping-item.dto';

@Component({
  selector: 'app-lista-compra',
  standalone: true,
  imports: [],
  templateUrl: './lista-compra.component.html',
  styleUrls: ['./lista-compra.component.css']
})
export class ListaCompraComponent implements OnInit {
  articulos: ShoppingItemDTO[] = [];
  cargando = false;
  filtroActual = 'pendiente';

  constructor(private servicio: ShoppingItemService) {}

  ngOnInit(): void {
    this.cargarArticulos();
  }

  cargarArticulos(): void {
    this.cargando = true;
    this.servicio.obtenerArticulos().subscribe({
      next: (items) => {
        this.articulos = items;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
      }
    });
  }

  itemsPendientes(): ShoppingItemDTO[] {
    return this.articulos.filter(i => !item.comprado);
  }

  itemsComprados(): ShoppingItemDTO[] {
    return this.articulos.filter(i => item.comprado);
  }

  cambiarEstado(item: ShoppingItemDTO): void {
    dto.comprado = !dto.comprado;
    this.servicio.actualizarArticulo(item.id!, dto).subscribe({
      next: () => {
        item.comprado = dto.comprado;
      }
    });
  }

  eliminarItem(id: number): void {
    if (confirm('¿Estás seguro de eliminar este artículo?')) {
      this.servicio.eliminarArticulo(id).subscribe(() => {
        this.cargarArticulos();
      });
    }
  }