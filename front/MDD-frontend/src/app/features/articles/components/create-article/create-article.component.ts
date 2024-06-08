import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArticleService } from '../../services/article.service';
import { TopicService } from '../../../topics/services/topic.service';
import { Router } from '@angular/router';
import { Article } from '../../intefaces/article.interface';
import { CreateArticleRequest } from '../../intefaces/createArticleRequest';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss'
})
export class CreateArticleComponent {
    public topics$ = this.topicService.getTopics();
    public articleForm = this.fb.group({
        topic: [
            '',
            [Validators.required, Validators.minLength(3)]
        ],
        title: [
            '',
            [Validators.required, Validators.minLength(3)]
        ],
        content: [
            '',
            [Validators.required, Validators.minLength(3)]
            ],
    });

    constructor(
        private fb: FormBuilder,
        private topicService: TopicService,
        private articleService: ArticleService,
        private router: Router,
    ) { }

    public submit(): void {
        const article = this.articleForm.value as CreateArticleRequest;
        this.articleService.createPublication(article).subscribe(
            () => this.router.navigate(['/publications'])
        );
    }
}
