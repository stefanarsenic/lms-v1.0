import {Component, Injector, ViewChild} from '@angular/core';
import {GenerickaTabelaComponent} from "../../../../genericko/genericka-tabela/genericka-tabela.component";
import {KorisnikService} from "../../../services/korisnik.service";
import { BrowserModule } from '@angular/platform-browser';
import { ButtonModule } from 'primeng/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {Table, TableModule} from 'primeng/table';
import {ProgressBarModule} from "primeng/progressbar";
import {TagModule} from "primeng/tag";
import {NgClass} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {MultiSelectModule} from "primeng/multiselect";
import {RegistrovaniKorisnik} from "../../../model/registrovaniKorisnik";
import {SliderModule} from "primeng/slider";
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {IconFieldModule} from "primeng/iconfield";
import {InputIconModule} from "primeng/inputicon";
import {InputTextModule} from "primeng/inputtext";
import {SortEvent} from "primeng/api";
@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    GenerickaTabelaComponent,
    ButtonModule,
    TableModule,
    ProgressBarModule,
    TagModule,
    NgClass,
    PaginatorModule,
    MultiSelectModule,
    SliderModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent extends AppGenerickoComponent<RegistrovaniKorisnik>{

  loading: boolean = true;
  @ViewChild('dt2') dt: Table | undefined;
  constructor(private injector: Injector) {
    super();
    const service = this.injector.get(KorisnikService);
    this.initialize(service);
    this.loading=false
  }

  ngOnInit() {
  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

}
