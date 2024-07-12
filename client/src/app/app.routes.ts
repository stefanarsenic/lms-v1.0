import { Routes } from '@angular/router';
import {MainComponent} from "./components/main/main.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {LoginComponent} from "./components/login/login.component";

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
  {path: "main", component: MainComponent},
  {path: "**", component: NotFoundComponent}
];
