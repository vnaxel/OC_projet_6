import { ChangeDetectorRef, Component, HostListener, OnInit, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
import { User } from './interfaces/user.interface';
import { SessionService } from './services/session.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    constructor(
        private authService: AuthService,
        private cdr: ChangeDetectorRef,
        private router: Router,
        private sessionService: SessionService) {
    }

    displayTopBar: boolean = true;
    isChecked: boolean = false;

    public ngOnInit(): void {
        console.log(this.isChecked)
        this.autoLog();
        if (this.sessionService.isLogged) {
            this.checkTopBarDisplay();
            this.router.navigate(['/publications']);
            return;
        }
    }

    public $isLogged(): Observable<boolean> {
        return this.sessionService.$isLogged();
    }

    public autoLog(): void {
        this.authService.me().subscribe(
            (user: User) => {
                this.sessionService.logIn(user);
                this.router.navigate(['/publications']);
            },
            (_) => {
                this.sessionService.logOut();
            }
        )
    }

    public shouldDisplayTopBar(): boolean {
        const currentUrl = this.router.url;
        if (currentUrl === '/') {
            return false;
        }
        if (currentUrl === '/login' || currentUrl === '/register') {
            return window.innerWidth > 520;
        }
        return true;
    }

    @HostListener('window:resize', ['$event'])
    onResize(event: any) {
        this.checkTopBarDisplay(); // Call whenever window is resized
    }

    private checkTopBarDisplay() {
        const currentUrl = this.router.url;
        if (currentUrl === '/') {
            this.displayTopBar = false;
        } else if (currentUrl === '/login' || currentUrl === '/register') {
            this.displayTopBar = window.innerWidth > 520;
        } else {
            this.displayTopBar = true;
        }
        // Trigger change detection explicitly
        this.cdr.detectChanges();
    }
}