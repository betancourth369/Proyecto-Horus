import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuLateralComponent } from '../shared/layout/menu-lateral/menu-lateral.component';
import { ContenedorPrincipalComponent } from '../shared/layout/contenedor-principal/contenedor-principal.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MenuLateralComponent, ContenedorPrincipalComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'Horus';
}