import { Injectable } from "@angular/core";
import { UserInterface } from "../features/auth/interfaces/user.interface";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { User } from "../interfaces/user.interface";
import { ChangeEmailOrUsernameRequest } from "../interfaces/changeEmailOrUsernameRequest.interface";
import { ChangePasswordRequest } from "../interfaces/changePasswordRequest.interface";

@Injectable({
    providedIn: "root"
})
export class UserService {
    private pathService = "http://localhost:8081/api/v1/user";
    constructor(private http: HttpClient) {}

    public changeEmailOrUsername(request: ChangeEmailOrUsernameRequest): Observable<UserInterface> {
        return this.http.put<UserInterface>(`${this.pathService}/me`, {
            email: request.email,
            username: request.username
        });
    }

    public changePassword(request: ChangePasswordRequest): Observable<User> {
        return this.http.put<User>(`${this.pathService}/me/password`, {
            oldPassword: request.oldPassword,
            newPassword: request.newPassword,
            confirmPassword: request.confirmPassword
        });
    }
}