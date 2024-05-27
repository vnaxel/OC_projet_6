import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { UserInterface } from './user.interface';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
    authService = inject(AuthService);
    http = inject(HttpClient);
  
    ngOnInit(): void {
      this.http
        .get<{ user: UserInterface }>('http://localhost:8081/api/v1/user/me')
        .subscribe({
          next: (response) => {
            console.log('response', response);
            this.authService.currentUserSig.set(response.user);
          },
          error: () => {
            this.authService.currentUserSig.set(null);
          },
        });
    }
  
    logout(): void {
      console.log('logout');
      localStorage.setItem('token', '');
      this.authService.currentUserSig.set(null);
    }
  }