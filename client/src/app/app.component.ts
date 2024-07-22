import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {NgOptimizedImage} from "@angular/common";
import {LoginService} from "./services/login.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgOptimizedImage],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'client';

  constructor(
    private loginService: LoginService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    if (!this.loginService.isAuthenticated()) {
      this.router.navigate(['/login']);
    }
  }
}
