import { RouterModule, Routes } from "@angular/router";
import { TopicListComponent } from "./list/topic-list.component";
import { NgModule } from "@angular/core";


const routes: Routes = [
    { title: 'Themes', path: '', component: TopicListComponent },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class TopicRoutingModule { }