import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { UserInterface } from '../../interfaces/user.interface';
import { User } from '../../../../interfaces/user.interface';
import { SessionService } from '../../../../services/session.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Errors } from '../../interfaces/errors.interface';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']

})
export class LoginComponent {
    public hide = true;
    public errors: string | undefined;

    public form = this.fb.group({
        emailOrUsername: ['', [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(20),
        ]],
        password: ['', [
            Validators.required,
            Validators.minLength(8),
            Validators.maxLength(20),
            Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/)
        ]],
    });
    constructor(private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        private sessionService: SessionService,
        private matSnackBar: MatSnackBar,
    ) { }

    public submit(): void {
        const loginRequest = this.form.value as LoginRequest;
        this.authService.login(loginRequest).subscribe({
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
                this.showErrors(err.error);
            },
        });
    }

    private showErrors(errs: Errors): void {
        this.errors = errs.errors.join('\n');
        // efface l'erreur apres 5 secondes
        setTimeout(() => this.errors = undefined, 5000);
    }
}

