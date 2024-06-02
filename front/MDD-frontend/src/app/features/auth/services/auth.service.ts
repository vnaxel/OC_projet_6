import { Injectable, inject, signal } from '@angular/core';
import { UserInterface } from '../interfaces/user.interface';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { Observable } from 'rxjs';
import { User } from '../../../interfaces/user.interface';


@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private pathService = 'http://localhost:8081/api/v1';

    constructor(private httpClient: HttpClient) { }

    public register(registerRequest: RegisterRequest): Observable<UserInterface> {
        return this.httpClient.post<UserInterface>(`${this.pathService}/signup`, registerRequest);
    }

    public login(loginRequest: LoginRequest): Observable<UserInterface> {
        return this.httpClient.post<UserInterface>(`${this.pathService}/signin`, loginRequest);
    }

    public me(): Observable<User> {
        return this.httpClient.get<User>(`${this.pathService}/user/me`);
    }
}