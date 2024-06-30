import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArticleService } from '../../services/article.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';
import { User } from '../../../../interfaces/user.interface';
import { CreateArticleRequest } from '../../intefaces/createArticleRequest';
import { HttpErrorResponse } from '@angular/common/http';
import { Errors } from '../../../auth/interfaces/errors.interface';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {
  public user: User | undefined;
  public errors: string | undefined;
  public articleForm = this.fb.group({
    topic: ['', [Validators.required]],
    title: ['', [Validators.required, Validators.minLength(3)]],
    content: ['', [Validators.required, Validators.minLength(3)]],
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.me().subscribe((user: User) => {
      this.user = user;
    });
  }

  private showErrors(errs: Errors): void {
    this.errors = errs.errors.join('\n');
    // efface l'erreur apres 5 secondes
    setTimeout(() => this.errors = undefined, 5000);
}

  public submit(): void {
    const article = this.articleForm.value as CreateArticleRequest;
    this.articleService.createPublication(article).subscribe({
        next: () => this.router.navigate(['/publications']),
        error: (err: HttpErrorResponse) => {
            this.showErrors(err.error);
        },
    });
  }
}
