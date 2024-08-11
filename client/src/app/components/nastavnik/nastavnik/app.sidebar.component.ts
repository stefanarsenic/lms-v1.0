import {Component, ElementRef, Input} from '@angular/core';
import {AppMenuComponent} from "./app.menu.component";
import {RouterLink, RouterModule, RouterOutlet} from "@angular/router";
import {LayoutService} from "./service/app.layout.service";

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

    @Input()
    model:any
    constructor(public layoutService: LayoutService, public el: ElementRef) { }


}

