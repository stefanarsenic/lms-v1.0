import { Component } from '@angular/core';
import {TestService} from "../../services/test.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main-student',
  standalone: true,
  imports: [],
  templateUrl: './main-student.component.html',
  styleUrl: './main-student.component.css'
})
export class MainStudentComponent {

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
