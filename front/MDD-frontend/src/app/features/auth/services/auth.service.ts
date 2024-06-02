import { Injectable, inject, signal } from '@angular/core';
import { UserInterface } from '../interfaces/user.interface';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { Router } from '@angular/router';


@Injectable({
    providedIn: 'root',
})
export class AuthService {
    http = inject(HttpClient);
    router = inject(Router);
    currentUserSig = signal<UserInterface | undefined | null>(undefined);

    login(loginRequest: LoginRequest) {
        return this.http.post<{ token: string, username: string, email: string }>(
            'http://localhost:8081/api/v1/signin',
            loginRequest
        ).subscribe((response) => {
            console.log('response', response);
            localStorage.setItem('token', response.token);
            this.currentUserSig.set({ email: response.email, token: response.token, username: response.username });
            this.router.navigateByUrl('/');
        });
    }

    register(registerRequest: RegisterRequest) {
        return this.http.post<{ token: string, username: string, email: string }>(
            'http://localhost:8081/api/v1/signup',
            registerRequest
        ).subscribe((response) => {
            console.log('response', response);
            localStorage.setItem('token', response.token);
            this.currentUserSig.set({ email: response.email, token: response.token, username: response.username });
            this.router.navigateByUrl('/');
        });
    }
}