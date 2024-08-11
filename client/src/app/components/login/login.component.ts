import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {LoginService} from "../../services/login.service";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PasswordModule} from "primeng/password";
import {CheckboxModule} from "primeng/checkbox";
import {ButtonDirective} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {InputTextModule} from "primeng/inputtext";
import {NgOptimizedImage} from "@angular/common";
import {LayoutService} from "../nastavnik/nastavnik/service/app.layout.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, PasswordModule, CheckboxModule, FormsModule, RouterLink, ButtonDirective, Ripple, InputTextModule, NgOptimizedImage],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent{

  forma=new FormGroup({
    "korisnickoIme":new FormControl(),
    "lozinka":new FormControl()
  })

  constructor(
    private router: Router,
    private loginService:LoginService,
    public layoutService: LayoutService
  ) {}

  login(){
    this.loginService.login(this.forma.value).subscribe(value => {
      if(this.loginService.proveraUloga(["ROLE_REGULAR"])){
        this.router.navigate(["/korisnik"])
      }else if(this.loginService.proveraUloga(["ROLE_ADMIN"])){
        this.router.navigate(["/admin"])
      }else if(this.loginService.proveraUloga(["ROLE_SLUZBA"])){
        this.router.navigate(["/studentska-sluzba"])
      }else if(this.loginService.proveraUloga(["ROLE_NASTAVNIK"])) {
        this.router.navigate(["/nastavnik"])
      }
      else if(this.loginService.proveraUloga(["ROLE_STUDENT"])) {
        this.router.navigate(["/student"])
      }else {
        this.router.navigate(["/main"])
      }

    })
  }

}
