import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';

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

    public onError = false;

    public form = this.fb.group({
      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],
      username: [
        '',
        [
          Validators.required,
          Validators.min(3),
          Validators.max(20)
        ]
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.min(3),
          Validators.max(40)
        ]
      ]
    });

    onSubmit(): void {
        const registerRequest = this.form.value as RegisterRequest;
        this.authService.register(registerRequest);
    }
}