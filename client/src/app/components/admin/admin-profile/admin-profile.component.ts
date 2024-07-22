import { Component } from '@angular/core';
import {KorisnikService} from "../../../services/korisnik.service";
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {UsersComponent} from "../users/users.component";
import {Button} from "primeng/button";

@Component({
  selector: 'app-admin-profile',
  standalone: true,
  imports: [
    NgIf,
    UsersComponent,
    Button
  ],
  templateUrl: './admin-profile.component.html',
  styleUrl: './admin-profile.component.css'
})
export class AdminProfileComponent {

  constructor(private korisnikService: KorisnikService,private router:Router) {
    this.onInit()
  }
  currentComponent: string = '';

  showComponent(component: string) {
    this.currentComponent = component;
  }
  username: string = ""
  brojStudenata:number=0;
  brojKorisnika:number=0;
  brojNastavnika:number=0;
  onInit(): void {
    let token = localStorage.getItem("token")
    if (token) {
      this.username = JSON.parse(atob(token.split(".")[1]))["username"]

    }
  }
  logout(){
    localStorage.removeItem("token")
    this.router.navigate(["/login"])
  }

}
