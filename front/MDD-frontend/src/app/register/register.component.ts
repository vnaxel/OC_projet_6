import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserInterface } from '../user.interface';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    standalone: true,
    imports: [ReactiveFormsModule],
})
export class RegisterComponent {
    fb = inject(FormBuilder);
    http = inject(HttpClient);
    authService = inject(AuthService);
    router = inject(Router);

    form = this.fb.nonNullable.group({
        username: ['', Validators.required],
        email: ['', Validators.required],
        password: ['', Validators.required],
    });

    onSubmit(): void {
        this.http
            .post<{ token: string, username: string, email: string }>('http://localhost:8081/api/v1/signup',
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