import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../../../../interfaces/user.interface";

@Injectable({
    providedIn: "root"
    })
export class TopicService {
    private pathService = "http://localhost:8081/api/v1";
    constructor(private http: HttpClient) {}

    public getTopics(): Observable<string[]> {
        return this.http.get<string[]>(`${this.pathService}/topic`);
    }

    public subscribeToTopic(topic: string): Observable<User> {
        return this.http.put<User>(`${this.pathService}/topic/subscribe/${topic}`, {});
    }

    public unsubscribeToTopic(topic: string): Observable<User> {
        return this.http.put<User>(`${this.pathService}/topic/unsubscribe/${topic}`, {});
    }
}