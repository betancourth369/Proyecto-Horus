import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-chip-estado',
  standalone: true,
  template: `
    <span class="chip {{ estado }}">{{ texto }}</span>
  `,
  styleUrls: ['./chip-estado.component.css']
})
export class ChipEstadoComponent {
  @Input() estado: 'pendiente' | 'en-curso' | 'completada' | 'vencida' = 'pendiente';
  texto = 'Pendiente';

  ngOnInit() {
    const textos = {
      pendiente: 'Pendiente',
      'en-curso': 'En curso',
      completada: 'Completada',
      vencida: 'Vencida'
    };
    this.texto = textos[this.estado] || this.estado;
  }
}