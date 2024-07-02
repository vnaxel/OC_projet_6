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
import { TopicService } from '../../features/topics/services/topic.service';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-me',
    templateUrl: './me.component.html',
    styleUrls: ['./me.component.scss']
})
export class MeComponent {

    public user: User | undefined;
    public subscribedTopics$: Observable<string[]> | undefined;
    public changingPassword: boolean = false;
    public userForm: FormGroup | undefined;
    public passwordForm: FormGroup | undefined;
    public userFormErrors: string | undefined;
    public passwordFormErrors: string | undefined;

    constructor(
        private authService: AuthService,
        private userService: UserService,
        private fb: FormBuilder,
        private sessionService: SessionService,
        private topicService: TopicService,
        private router: Router
    ) { }

    public ngOnInit(): void {
        this.authService.me().subscribe(
            (user: User) => {
                this.user = user
                this.initChangeEmailOrUsernameForm();
                this.subscribedTopics$ = this.sessionService.getUserInterrestedTopics();
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
            this.userForm?.reset();
            this.userFormErrors = undefined;
            return
        }
        this.initChangeEmailOrUsernameForm();
        this.passwordForm?.reset();
        this.passwordFormErrors = undefined;
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
            username: [this.user?.username || '',
                [
                    Validators.required,
                    Validators.minLength(4),
                    Validators.maxLength(20),
                ]
            ],
            email: [this.user?.email || '',
                [
                    Validators.required,
                    Validators.email,
                ]
            ]
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
                this.showUserErrors(err.error);
            },
        });
    }

    private showUserErrors(errs: Errors): void {
        this.userFormErrors = errs.errors.join('\n');
        // efface l'erreur apres 5 secondes
        setTimeout(() => this.userFormErrors = undefined, 5000);
    }

    public submitChangePassword() {
        const passwordRequest = this.passwordForm!.value as ChangePasswordRequest;
        this.userService.changePassword(passwordRequest).subscribe({
            next: (response: User) => {
                this.sessionService.logIn(response);
                this.router.navigate(['/me'])
                this.initChangeEmailOrUsernameForm();
                this.changingPassword = false;
            },
            error: (err: HttpErrorResponse) => {
                this.showPasswordErrors(err.error);
            },
    });
    }

    private showPasswordErrors(errs: Errors): void {
        this.passwordFormErrors = errs.errors.join('\n');
        // efface l'erreur apres 5 secondes
        setTimeout(() => this.passwordFormErrors = undefined, 5000);
    }

    public logout(): void {
        this.sessionService.logOut();
        this.router.navigate(['/login'])
    }


    public unsubscribeToTopic(topic: string): void {
        this.topicService.unsubscribeToTopic(topic).subscribe((user) => {
            this.sessionService.logIn(user);
            this.ngOnInit();
        });
    }
}