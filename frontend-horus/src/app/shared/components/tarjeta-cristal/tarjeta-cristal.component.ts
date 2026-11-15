import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tarjeta-cristal',
  standalone: true,
  template: `
    <div class="tarjeta-cristal" [class.completada]="completada">
      <ng-content></ng-content>
    </div>
  `,
  styleUrls: ['./tarjeta-cristal.component.css']
})
export class TarjetaCristalComponent {
  @Input() completada = false;
}