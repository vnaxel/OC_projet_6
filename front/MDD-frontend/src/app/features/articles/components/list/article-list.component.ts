import { Component } from '@angular/core';
import { ArticleService } from '../../services/article.service';

@Component({
    selector: 'app-article-list',
    templateUrl: './article-list.component.html',
    styleUrl: './article-list.component.scss'
})
export class ArticleListComponent {
    public articles$ = this.articleService.getPublicationsForUser();

    constructor(private articleService: ArticleService) { }


}
