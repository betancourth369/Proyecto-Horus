import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-boton',
  standalone: true,
  template: `
    <button
      class="btn {{ tipo }}"
      [class.btn-primario]="tipo === 'primario'"
      [class.btn-secundario]="tipo === 'secundario'"
      [disabled]="deshabilitado"
      (click)="clic.emit()"
    >
      <ng-content></ng-content>
    </button>
  `,
  styleUrls: ['./boton.component.css']
})
export class BotonComponent {
  @Input() tipo: 'primario' | 'secundario' = 'primario';
  @Input() deshabilitado = false;
  @Output() clic = new EventEmitter<void>();

  clic() {
    this.clic.emit();
  }
}