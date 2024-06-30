import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../../services/article.service';
import { Article } from '../../intefaces/article.interface';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentPublicationRequest } from '../../intefaces/commentPublicationRequest';
import { HttpErrorResponse } from '@angular/common/http';
import { Errors } from '../../../auth/interfaces/errors.interface';

@Component({
    selector: 'app-article',
    templateUrl: './article.component.html',
    styleUrl: './article.component.scss'
})
export class ArticleComponent {
    public article: Article | undefined;
    public errors: string | undefined;
    public commentForm = this.fb.group({
        content: [
            '',
            [Validators.required, Validators.minLength(3)]
        ]
    });

    constructor(
        private route: ActivatedRoute,
        private articleService: ArticleService,
        private fb: FormBuilder,
    ) { }

    public ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id')!
        this.articleService.getPublicationById(id)
            .subscribe((article: Article) => {
                this.article = article;
            });
    }

    public back() {
        window.history.back();
    }

    public submitComment(): void {
        const commentRequest = this.commentForm.value as CommentPublicationRequest;
        this.commentForm.reset();
        this.articleService.commentPublication(this.article!.id, commentRequest).subscribe({
            next: () => this.ngOnInit(),
            error: (err: HttpErrorResponse) => {
                this.showErrors(err.error);
            },
        }
        );
    }

    private showErrors(errs: Errors): void {
        this.errors = errs.errors.join('\n');
        // efface l'erreur apres 5 secondes
        setTimeout(() => this.errors = undefined, 5000);
    }

    submitForm() {
        this.commentForm.markAllAsTouched();
        if (this.commentForm.valid) {
          this.submitComment();
        }
      }
}
