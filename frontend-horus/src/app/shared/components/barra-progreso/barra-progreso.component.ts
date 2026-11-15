import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-barra-progreso',
  standalone: true,
  template: `
    <div class="barra-progreso">
      <div class="barra-progreso-relleno" [style.width.porcentaje]="porcentaje"></div>
    </div>
    <span class="texto-progreso">{{ porcentaje }}%</span>
  `,
  styleUrls: ['./barra-progreso.component.css']
})
export class BarraProgresoComponent {
  @Input() porcentaje = 0;
}