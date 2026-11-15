import { Component, OnInit } from '@angular/core';
import { TareaService } from '../../core/services/tarea.service';
import { Tarea } from '../../core/modelos/tarea.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ChipEstadoComponent } from '../../../shared/components/chip-estado/chip-estado.component';
import { TarjetaCristalComponent } from '../../../shared/components/tarjeta-cristal/tarjeta-cristal.component';
import { BotonComponent } from '../../../shared/components/boton/boton.component';
import { BarraProgresoComponent } from '../../../shared/components/barra-progreso/barra-progreso.component';

@Component({
  selector: 'app-lista-tareas',
  standalone: true,
  imports: [CommonModule, ChipEstadoComponent, TarjetaCristalComponent, BotonComponent, BarraProgresoComponent],
  templateUrl: './lista-tareas.component.html',
  styleUrls: ['./lista-tareas.component.css']
})
export class ListaTareasComponent implements OnInit {
  tareas: Tarea[] = [];
  cargando = false;
  filtroActual = 'todos';

  constructor(private tareaService: TareaService, private router: Router) {}

  ngOnInit(): void {
    this.cargarTareas();
  }

  cargarTareas(): void {
    this.cargando = true;
    this.tareaService.obtenerTareas().subscribe({
      next: (tareas) => {
        this.tareas = tareas;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
      }
    });
  }

  nuevaTarea(): void {
    this.router.navigate(['/tareas/nueva']);
  }

  cambiarEstado(tarea: Tarea): void {
    this.tareaService.actualizarTarea(tarea.id!, { completado: !tarea.completado }).subscribe(() => {
      this.cargarTareas();
    });
  }

  eliminarTarea(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta tarea?')) {
      this.tareaService.eliminarTarea(id).subscribe(() => {
        this.cargarTareas();
      });
    }
  }

  filtrar(estado: string): void {
    this.filtroActual = estado;
  }

  esPendiente(tarea: Tarea): boolean {
    return !tarea.completado;
  }

  getTotalTareas(): number {
    return this.tareas.length;
  }

  getPendientes(): number {
    return this.tareas.filter(t => !t.completado).length;
  }

  getCompletadas(): number {
    return this.tareas.filter(t => t.completado).length;
  }

  get tareasFiltradas() {
    if (this.filtroActual === 'todos') return this.tareas;
    if (this.filtroActual === 'pendientes') return this.tareas.filter(t => !t.completado);
    if (this.filtroActual === 'completadas') return this.tareas.filter(t => t.completado);
    return this.tareas;
  }

  progreso(tarea: Tarea): number {
    return tarea.completado ? 100 : 0;
  }

  porcentajeCompletado(tarea: Tarea): string {
    return this.progreso(tarea) + '%';
  }
}