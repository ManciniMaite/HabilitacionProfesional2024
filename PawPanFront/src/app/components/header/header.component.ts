import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../services/auth.service';
import { Usuario } from '../../model/Usuario';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  usuario:Usuario | null = null;

  constructor(
    private authService: AuthService
  ){  }

  ngOnInit() {
    this.authService.usuario$.subscribe(usuario => {
      console.log('getUS')
      this.usuario = usuario;
    });
  }
}
