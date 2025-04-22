import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterLink } from '@angular/router';
import { MenuItems } from '../model/MenuItems';
import { menuItems } from '../model/data/data-MenuItems';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatCardModule, 
    MatFormFieldModule, 
    MatInputModule, 
    CommonModule, 
    ReactiveFormsModule,
    RouterLink,
    MatButtonModule,
    MatIconModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  menuItems: MenuItems[] = menuItems;
  role: string

  constructor(private routes: Router,
    private authService: AuthService
  ){}

  ngOnInit(): void {
    this.authService.usuario$.subscribe(usuario => {
      this.role = usuario?.rol
    });
  }

  navegar(item:MenuItems){
    this.routes.navigate([item.path]);
  }

  esRol(item: MenuItems): boolean{
    return item.role.includes(this.role);
  }

}
