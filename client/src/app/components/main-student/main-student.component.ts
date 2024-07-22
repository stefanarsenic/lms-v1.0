import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-main-student',
  standalone: true,
  imports: [],
  templateUrl: './main-student.component.html',
  styleUrl: './main-student.component.css'
})
export class MainStudentComponent {

  constructor(private router: Router) {
  }


  logout(){
    localStorage.removeItem("token")
    this.router.navigate(["/login"])
  }
}
