import { Routes } from '@angular/router';
import {HomePageComponent} from "./components/home-page/home-page.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {LoginComponent} from "./components/login/login.component";
import {authGuard} from "./guards/auth.guard";
import {UserProfileComponent} from "./components/user/user-profile/user-profile.component";
import {RegisterComponent} from "./components/user/register/register.component";
import {MainStudentComponent} from "./components/main-student/main-student.component";

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
  {path: "main", component: MainStudentComponent,canActivate:[authGuard],data:{
    allowedRoles:["ROLE_NASTAVNIK","ROLE_ADMIN"]
    }},
  {
    path:"korisnik",
    component: UserProfileComponent,
    canActivate:[authGuard],data:{
      allowedRoles:["ROLE_REGULAR"]
    }
  },
  {
    path:"register",
    component: RegisterComponent
    // canActivate:[authGuard],data:{
    //   allowedRoles:["ROLE_REGULAR"]
    // }
  }
];
