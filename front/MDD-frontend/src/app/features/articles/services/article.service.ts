import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../../../interfaces/user.interface";
import { Article } from "../intefaces/article.interface";
import { CreateArticleRequest } from "../intefaces/createArticleRequest";
import { CommentPublicationRequest } from "../intefaces/commentPublicationRequest";
@Injectable({
    providedIn: "root"
    })
export class ArticleService {
    private pathService = "http://localhost:8081/api/v1";
    constructor(private http: HttpClient) {}

    public getPublicationsForUser(): Observable<Article[]> {
        return this.http.get<Article[]>(`${this.pathService}/publication`);
    }

    public getPublicationById(id: string): Observable<Article> {
        return this.http.get<Article>(`${this.pathService}/publication/${id}`, {});
    }

    public createPublication(request: CreateArticleRequest): Observable<Article> {
        return this.http.post<Article>(`${this.pathService}/publication`, {
            title: request.title,
            content: request.content,
            topic: request.topic
        });
    }

    public commentPublication(id: number, request: CommentPublicationRequest): Observable<Article> {
        return this.http.post<Article>(`${this.pathService}/publication/${id}/comment`, {
            content: request.content
        });
    }
}