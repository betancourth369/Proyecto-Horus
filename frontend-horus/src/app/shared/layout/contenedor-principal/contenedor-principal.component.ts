import { Component, input } from '@angular/core';

@Component({
  selector: 'app-contenedor-principal',
  standalone: true,
  imports: [],
  template: `
    <div class="contenido-principal">
      <ng-content></div>
    </div>
  `,
  styles: [`
    .contenido-principal {
      flex: 1;
      padding: 2rem;
      background-color: #14100c;
      min-height: 100vh;
      overflow-y: auto;
    }
  `]
})
export class ContenedorPrincipalComponent {
  @Input() titulo = '';
}