import { OnInit } from '@angular/core';
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

    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            {
                label: 'Home',
                items: [
                    { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['nastavnik'] }
                ]
            },
          {
            label: 'Predmeti',
            items: [
              { label: 'Pregled Predmeta', icon: 'pi pi-fw pi-book', routerLink: 'spisak-predmeta' },
              { label: 'Uređivanje Silabusa', icon: 'pi pi-fw pi-pencil', routerLink: 'uredjivanje-silabusa' },
              { label: 'Raspored Ishoda', icon: 'pi pi-fw pi-calendar', routerLink: ['/predmeti/ishodi'] },
              { label: 'Instrumenti Evaluacije', icon: 'pi pi-fw pi-file-o', routerLink: ['/predmeti/evaluacija'] },
              { label: 'Upravljanje Obaveštenjima', icon: 'pi pi-fw pi-bell', routerLink: ['/predmeti/obavestenja'] }
            ]
          },
          {
            label: 'Studenti',
            items: [
              { label: 'Spisak Studenata', icon: 'pi pi-fw pi-users', routerLink: ['studenti-spisak'] },
              { label: 'Pretraga Studenata', icon: 'pi pi-fw pi-search', routerLink: 'pretraga-studenata' },
              { label: 'Podaci o Studentu', icon: 'pi pi-fw pi-id-card', routerLink: ['/studenti/podaci'] },
              { label: 'Unos Ocena', icon: 'pi pi-fw pi-check-square', routerLink: ['/studenti/ocene'] }
            ]
          }

        ];
    }
}
