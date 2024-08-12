import { Component } from '@angular/core';
import {Predmet} from "../../../model/predmet";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {MessageService} from "primeng/api";
import {PredmetService} from "../../../services/predmet.service";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {PanelModule} from "primeng/panel";
import {TableModule} from "primeng/table";
import {AccordionModule} from "primeng/accordion";
import {DatePipe, NgForOf, NgIf, NgStyle} from "@angular/common";
import {ObavestenjaService} from "../../../services/obavestenja.service";
import {ButtonDirective} from "primeng/button";
import {ChipsModule} from "primeng/chips";
import {TagModule} from "primeng/tag";
import {ChipModule} from "primeng/chip";
import {DialogModule} from "primeng/dialog";
import {ListboxModule} from "primeng/listbox";
import {FormsModule} from "@angular/forms";
import {CardModule} from "primeng/card";

@Component({
  selector: 'app-pregled-obavestenja',
  standalone: true,
  imports: [
    PanelModule,
    TableModule,
    AccordionModule,
    NgIf,
    ButtonDirective,
    NgForOf,
    ChipsModule,
    DatePipe,
    TagModule,
    ChipModule,
    DialogModule,
    ListboxModule,
    FormsModule,
    CardModule,
    NgStyle
  ],
  templateUrl: './pregled-obavestenja.component.html',
  styleUrl: './pregled-obavestenja.component.css'
})
export class PregledObavestenjaComponent {
  //TODO:Prvier da izgleda lepse akd ima vise predmeta ili vise studentNaGodini
  loading: boolean = true;
  studentUsername: string | null;
  studentNaGodini!: StudentNaGodini[];
  obavestenja: any[] = [];
  selectedObavestenje: any | null = null;
  obavestenjaModalVisible: boolean = false;
  searchText: string = '';

  constructor(
    private messageService: MessageService,
    private predmetService: PredmetService,
    private studentNaGodiniService: StudentNaGodiniService,
    private obavestenjeService: ObavestenjaService
  ) {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.studentUsername = JSON.parse(atob(token.split(".")[1])).username;
    } else {
      this.studentUsername = null;
    }

    if (this.studentUsername) {
      this.studentNaGodiniService.getAllByStudentUsername(this.studentUsername).subscribe({
        next: (data) => {
          this.studentNaGodini = data;
          this.loading = false;
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Unable to fetch predmeti' });
          this.loading = false;
        }
      });
    } else {
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Invalid user token' });
      this.loading = false;
    }
  }

  openObavestenjaModal(predmet: any): void {
    this.obavestenjaModalVisible = true;
    this.loadObavestenja(predmet.id);
  }

  loadObavestenja(predmetId: number): void {
    this.obavestenjeService.getObavestenjaByPredmetId(predmetId).subscribe({
      next: (obavestenja) => {
        this.obavestenja = obavestenja.map(obavestenje => {
          obavestenje.vremePostavljanja = this.convertArrayToDate(obavestenje.vremePostavljanja as any);
          return obavestenje;
        });
      },
      error: (error) => console.error('Error fetching obavestenja:', error)
    });
  }

  viewObavestenjeDetails(obavestenje: any): void {
    this.selectedObavestenje = obavestenje;
  }

  convertArrayToDate(dateArray: number[]): Date {
    return new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5], Math.floor(dateArray[6] / 1000000));
  }

  get getFilteredObavestenja() {
    if (this.searchText) {
      return this.obavestenja.filter(obavestenje =>
        obavestenje.naslov.toLowerCase().includes(this.searchText.toLowerCase()) ||
        obavestenje.sadrzaj.toLowerCase().includes(this.searchText.toLowerCase())
      );
    }
    return this.obavestenja;
  }

}
