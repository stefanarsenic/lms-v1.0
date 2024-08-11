import {Input, OnInit} from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';
import {NgForOf, NgIf} from "@angular/common";
import {AppMenuitemComponent} from "./app.menuitem.component";
import {RouterLink, RouterModule, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    NgForOf,
    AppMenuitemComponent,
    NgIf,
    RouterOutlet,
    RouterLink,
    RouterModule
  ],
  templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {
    @Input()
    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {

    }
}
