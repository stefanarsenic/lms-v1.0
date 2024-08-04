import { Component, ElementRef } from '@angular/core';
import {AppMenuComponent} from "./app.menu.component";
import {RouterLink, RouterModule, RouterOutlet} from "@angular/router";
import {LayoutTestService} from "../../../services/layout-test.service";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    AppMenuComponent,
    RouterOutlet,
    RouterLink,
    RouterModule
  ],
  templateUrl: './app.sidebar.component.html'
})
export class AppSidebarComponent {
    constructor(public layoutService: LayoutTestService, public el: ElementRef) { }
}

