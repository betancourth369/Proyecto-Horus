import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu-lateral',
  standalone: true,
  imports: [],
  template: `
    <nav class="menu-lateral">
      <div class="logo">Horus</div>
      <nav class="menu">
        <a routerLink="/tareas" [routerLinkActive]="active" [routerLinkActiveOptions]="{exact: true}">
          <span>📋</span> Tareas
        </a>
        <a routerLink="/compra" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
          <span>🛒</span> Lista de la compra
        </a>
        <a routerLink="/despensa" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
          <span>🧊</span> Despensa
        </a>
        <a routerLink="/clima" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
          <span>🌤️</span> Clima
        </a>
      </nav>
    </nav>
  `,
  styles: [`
    :host {
      display: block;
      position: fixed;
      top: 0;
      left: 0;
      width: 260px;
      height: 100vh;
      background: #1a0f2a;
      color: #e6e0d2;
      border-right: 1px solid rgba(255, 255, 255, 0.09);
      z-index: 1000;
      padding-top: 48px;
      box-sizing: border-box;
    }
    .logo {
      font-size: 1.75rem;
      font-weight: 700;
      color: #ffcb7a;
      padding: 24px;
      margin-bottom: 24px;
      text-align: center;
      font-family: 'SF Pro Text', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
      letter-spacing: -0.5px;
    }
    nav {
      padding: 0 8px;
    }
    nav a {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 12px 16px;
      border-radius: 12px;
      color: #b3a692;
      text-decoration: none;
      font-size: 0.95rem;
      transition: all 0.2s ease;
      cursor: pointer;
    }
    nav a:hover {
      background: rgba(255, 255, 255, 0.05);
      color: #ffcb7a;
    }
    nav a.active {
      background: rgba(255, 203, 78, 0.15);
      color: #ffcb7a;
      border-left: 3px solid #ffcb7a;
    }
  `]
})
export class MenuLateralComponent {}