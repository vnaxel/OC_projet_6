import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArticleService } from '../../services/article.service';
import { TopicService } from '../../../topics/services/topic.service';
import { Router } from '@angular/router';
import { Article } from '../../intefaces/article.interface';
import { CreateArticleRequest } from '../../intefaces/createArticleRequest';
import { AuthService } from '../../../auth/services/auth.service';
import { User } from '../../../../interfaces/user.interface';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss'
})
export class CreateArticleComponent {
    public user: User | undefined;
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

    public ngOnInit(): void {
        this.authService.me().subscribe(
            (user: User) => {
                this.user = user
            }
        )
    }

    constructor(
        private fb: FormBuilder,
        private authService: AuthService,
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
