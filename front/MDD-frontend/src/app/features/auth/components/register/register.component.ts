import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { SessionService } from '../../../../services/session.service';
import { UserInterface } from '../../interfaces/user.interface';
import { User } from '../../../../interfaces/user.interface';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { Errors } from '../../interfaces/errors.interface';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

    public onError = false;

    public form = this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        username: ['', [Validators.required, Validators.min(3)]],
        password: ['', [Validators.required, Validators.min(3)]]
    });

    constructor(private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        private sessionService: SessionService,
        private matSnackBar: MatSnackBar,
    ) { }


    public submit(): void {
        const registerRequest = this.form.value as RegisterRequest;
        this.authService.register(registerRequest).subscribe({
            next: (response: UserInterface) => {
                localStorage.setItem('token', response.token);
                this.authService.me().subscribe((user: User) => {
                    this.sessionService.logIn(user);
                    if (!this.sessionService.user?.interestedTopics) {
                        this.router.navigate(['/themes'])
                        return
                    }
                    this.router.navigate(['/publications'])
                });
            },
            error: (err: HttpErrorResponse) => {
                this.onError = true,
                    this.showErrors(err.error);
            },
        });
    }

    private showErrors(errs: Errors): void {
        let message = Object.values(errs.errors).join(' - ');
        this.matSnackBar.open(
            message, 'Close', {
            duration: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'top',
        });
    }
}