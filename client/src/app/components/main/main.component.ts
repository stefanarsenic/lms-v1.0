import { Component } from '@angular/core';
import {TestService} from "../../services/test.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

  constructor(private testService:TestService, private router: Router) {
  }


  getNastavniciTest(){
    this.testService.getNastavnici().subscribe(r=>{
      console.log(r)
    })
  }

  logout(){
    localStorage.removeItem("token")
    this.router.navigate(["/login"])
  }
}
