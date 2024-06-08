import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MeComponent } from './components/me/me.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { LoginComponent } from './features/auth/components/login/login.component';

const routes: Routes = [
    {
        path: 'publications',
        canActivate: [AuthGuard],
        loadChildren: () => import('./features/articles/article.module').then(m => m.ArticlesModule)
    },
    {
        path: 'themes',
        canActivate: [AuthGuard],
        loadChildren: () => import('./features/topics/topic.module').then(m => m.TopicsModule)
    },
    {
        path: '',
        canActivate: [UnauthGuard],
        loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule),
    },
    {
        path: 'me',
        canActivate: [AuthGuard],
        component: MeComponent
    },
    { path: '404', component: NotFoundComponent },
    { path: '**', redirectTo: '404' },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }