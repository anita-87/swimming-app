import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProfilesComponent} from "./profiles/profiles.component";

const routes: Routes = [
  { path: '', redirectTo: 'profiles/swim', pathMatch: "full" },
  { path: 'profiles/:type', component: ProfilesComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
