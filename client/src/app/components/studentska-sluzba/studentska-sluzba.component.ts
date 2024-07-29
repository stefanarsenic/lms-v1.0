import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-studentska-sluzba',
  standalone: true,
    imports: [
        RouterLink,
        RouterOutlet
    ],
  templateUrl: './studentska-sluzba.component.html',
  styleUrl: './studentska-sluzba.component.css'
})
export class StudentskaSluzbaComponent implements OnInit{

  username: string = "";

  constructor(
    private router: Router,
  ) {}
  ngOnInit(): void {
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
