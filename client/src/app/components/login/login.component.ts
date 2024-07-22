import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../../services/login.service";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
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
    private loginService:LoginService
  ) {}

  login(){
    this.loginService.login(this.forma.value).subscribe(value => {
      if(this.loginService.proveraUloga(["ROLE_REGULAR"])){
        this.router.navigate(["/korisnik"])
      }else if(this.loginService.proveraUloga(["ROLE_ADMIN"])){
        this.router.navigate(["/admin"])
      }else {
        this.router.navigate(["/main"])
      }

    })
  }

}
