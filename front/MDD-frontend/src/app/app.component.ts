import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { UserInfos } from './interfaces/userInfos.interface';
import { AuthService } from './features/auth/services/auth.service';

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
        const token = localStorage.getItem('token');
        if (token) {
            this.http.get<UserInfos>('http://localhost:8081/api/v1/user/me', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }).subscribe((response) => {
                this.authService.currentUserSig.set({token, email: response.email, username: response.username});
            });
        }
    }

    logout(): void {
        console.log('logout');
        localStorage.setItem('token', '');
        this.authService.currentUserSig.set(null);
    }
}