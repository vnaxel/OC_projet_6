import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { User } from '../interfaces/user.interface';

@Injectable({
    providedIn: 'root'
})
export class SessionService {

    public isLogged = false;
    public user: User | undefined;

    private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

    public $isLogged(): Observable<boolean> {
        return this.isLoggedSubject.asObservable();
    }

    public getUserInterrestedTopics(): Observable<string[]> {
        return of(this.user?.interestedTopics || []);
    }
    
    public logIn(user: User): void {
        this.user = user;
        this.isLogged = true;
        this.next();
    }

    public logOut(): void {
        localStorage.removeItem('token');
        this.user = undefined;
        this.isLogged = false;
        this.next();
    }

    private next(): void {
        this.isLoggedSubject.next(this.isLogged);
    }
}