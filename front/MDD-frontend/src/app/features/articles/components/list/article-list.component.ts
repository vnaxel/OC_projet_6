import { Component } from '@angular/core';
import { ArticleService } from '../../services/article.service';
import { AuthService } from '../../../auth/services/auth.service';
import { SessionService } from '../../../../services/session.service';

@Component({
    selector: 'app-article-list',
    templateUrl: './article-list.component.html',
    styleUrl: './article-list.component.scss'
})
export class ArticleListComponent {
    public articles$ = this.articleService.getPublicationsForUser();
    public subscribedTopics$ = this.sessionService.user?.interestedTopics;

    constructor(private articleService: ArticleService, private sessionService: SessionService) { }
}
