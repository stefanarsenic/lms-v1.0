import { Routes } from '@angular/router';
import {MainComponent} from "./components/main/main.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {LoginComponent} from "./components/login/login.component";
import {authGuard} from "./guards/auth.guard";

export const routes: Routes = [
  {
    path: "",
    component: HomePageComponent,
    children:[
      {
        path: "login",
        component: LoginComponent
      }
    ]
  },
  {path: "main", component: MainComponent,canActivate:[authGuard],data:{
    allowedRoles:["ROLE_NASTAVNIK"]
    }},
  {path: "**", component: NotFoundComponent}
];
