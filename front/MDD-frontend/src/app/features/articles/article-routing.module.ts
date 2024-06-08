import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import { ArticleListComponent } from "./components/list/article-list.component";
import { ArticleComponent } from "./components/article/article.component";
import { CreateArticleComponent } from "./components/create-article/create-article.component";


const routes: Routes = [
    { title: 'Publications', path: '', component: ArticleListComponent },
    { title: 'Publication', path: 'article/:id', component: ArticleComponent },
    { title: 'Creer publication', path: 'create', component: CreateArticleComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ArticleRoutingModule { }