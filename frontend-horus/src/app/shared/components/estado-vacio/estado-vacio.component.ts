import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-estado-vacio',
  standalone: true,
  template: `
    <div class="estado-vacio">
      <div class="icono">📭</div>
      <h3>{{ titulo }}</h3>
      <p>{{ mensaje }}</p>
      <button *ngIf="accion" class="btn-primario" (click)="accion.emit()">{{ textoBoton }}</button>
    </div>
  `,
  styleUrls: ['./estado-vacio.component.css']
})
export class EstadoVacioComponent {
  @Input() titulo = 'No hay elementos';
  @Input() mensaje = 'No se encontraron elementos.';
  @Input() textoBoton = 'Agregar uno';
  @Input() accion: () => void = () => {};
}