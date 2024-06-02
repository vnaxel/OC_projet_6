import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { CommonModule } from '@angular/common';
import { UserInterface } from '../../interfaces/user.interface';
import { User } from '../../../../interfaces/user.interface';
import { SessionService } from '../../../../services/session.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
})
export class LoginComponent {
    public hide = true;
    public onError = false;

    public form = this.fb.group({
        emailOrUsername: [
            '',
            [
                Validators.required,
                Validators.min(3)
            ]
        ],
        password: [
            '',
            [
                Validators.required,
                Validators.min(3)
            ]
        ]
    });
    constructor(private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        private sessionService: SessionService) { }

    public submit(): void {
        const loginRequest = this.form.value as LoginRequest;
        this.authService.login(loginRequest).subscribe(
            (response: UserInterface) => {
                localStorage.setItem('token', response.token);
                this.authService.me().subscribe((user: User) => {
                    this.sessionService.logIn(user);
                    this.router.navigate(['/themes'])
                });
                this.router.navigate(['/themes'])
            },
            error => this.onError = true
        );
    }
}

