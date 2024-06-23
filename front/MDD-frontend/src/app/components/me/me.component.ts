import { Component, OnInit } from '@angular/core';
import { User } from '../../interfaces/user.interface';
import { AuthService } from '../../features/auth/services/auth.service';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SessionService } from '../../services/session.service';
import { ChangeEmailOrUsernameRequest } from '../../interfaces/changeEmailOrUsernameRequest.interface';
import { UserInterface } from '../../features/auth/interfaces/user.interface';
import { ChangePasswordRequest } from '../../interfaces/changePasswordRequest.interface';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { Errors } from '../../features/auth/interfaces/errors.interface';

@Component({
    selector: 'app-me',
    templateUrl: './me.component.html',
    styleUrls: ['./me.component.scss']
})
export class MeComponent {

    public user: User | undefined;
    public changingPassword: boolean = false;
    public userForm: FormGroup | undefined;
    public passwordForm: FormGroup | undefined;

    constructor(
        private authService: AuthService,
        private userService: UserService,
        private route: ActivatedRoute,
        private matSnackBar: MatSnackBar,
        private fb: FormBuilder,
        private sessionService: SessionService,
        private router: Router
    ) { }

    public ngOnInit(): void {
        this.authService.me().subscribe(
            (user: User) => {
                this.user = user
                this.initChangeEmailOrUsernameForm();
            }
        )
    }

    public back() {
        window.history.back();
    }

    public switchMode() {
        this.changingPassword = !this.changingPassword;
        if (this.changingPassword) {
            this.initPasswordForm();
            return
        }
        this.initChangeEmailOrUsernameForm();
    }

    public initPasswordForm() {
        this.passwordForm = this.fb.group({
            oldPassword: ['',
                [
                    Validators.required,
                    Validators.minLength(8),
                    Validators.maxLength(20),
                    Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/)

                ]
            ],
            newPassword: ['',
                [
                    Validators.required,
                    Validators.minLength(8),
                    Validators.maxLength(20),
                    Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/)
                ]
            ],
            confirmPassword: ['',
                [
                    Validators.required,
                    Validators.minLength(8),
                    Validators.maxLength(20),
                    Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/)
                ]
            ],
        });
    }

    public initChangeEmailOrUsernameForm() {
        this.userForm = this.fb.group({
            username: [this.user!.username],
            email: [this.user!.email]
        });
    }

    public submitChangeEmailOrUsername() {
        const userRequest = this.userForm!.value as ChangeEmailOrUsernameRequest;
        this.userService.changeEmailOrUsername(userRequest).subscribe({
            next: (response: UserInterface) => {
                localStorage.setItem('token', response.token);
                this.authService.me().subscribe((user: User) => {
                    this.sessionService.logIn(user);
                    this.router.navigate(['/me'])
                });
                this.router.navigate(['/me'])
            },
            error: (err: HttpErrorResponse) => {
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

    public submitChangePassword() {
        const passwordRequest = this.passwordForm!.value as ChangePasswordRequest;
        this.userService.changePassword(passwordRequest).subscribe(
            (response: User) => {
                this.sessionService.logIn(response);
                this.router.navigate(['/me'])
                this.changingPassword = false;
            }
        );
    }

    public logout(): void {
        this.sessionService.logOut();
        this.router.navigate(['/login'])
    }
}