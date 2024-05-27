import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { UserInterface } from '../user.interface';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    standalone: true,
    imports: [ReactiveFormsModule],
})
export class LoginComponent {
    fb = inject(FormBuilder);
    http = inject(HttpClient);
    authService = inject(AuthService);
    router = inject(Router);

    form = this.fb.nonNullable.group({
        emailOrUsername: ['', Validators.required],
        password: ['', Validators.required],
    });

    onSubmit(): void {
        this.http
            .post<{token: string, username: string, email: string}>(
                'http://localhost:8081/api/v1/signin',
                this.form.getRawValue(),
            )
            .subscribe((response) => {
                console.log('response', response);
                localStorage.setItem('token', response.token);
                this.authService.currentUserSig.set({ email: response.email, token: response.token, username: response.username });
                this.router.navigateByUrl('/');
            });
    }
}