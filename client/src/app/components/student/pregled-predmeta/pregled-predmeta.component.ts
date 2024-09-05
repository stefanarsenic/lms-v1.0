import {Component, OnInit} from '@angular/core';
import {Predmet} from "../../../model/predmet";
import {MessageService, PrimeTemplate} from "primeng/api";
import {PredmetService} from "../../../services/predmet.service";
import {TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {Button} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {ToastModule} from "primeng/toast";
import {PanelModule} from "primeng/panel";
import {CardModule} from "primeng/card";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {MessageModule} from "primeng/message";
import {VirtualScrollerModule} from "primeng/virtualscroller";
import {AccordionModule} from "primeng/accordion";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {ChipModule} from "primeng/chip";

@Component({
  selector: 'app-pregled-predmeta',
  standalone: true,
  imports: [
    Button,
    PrimeTemplate,
    Ripple,
    TableModule,
    ToastModule,
    PanelModule,
    CardModule,
    NgIf,
    NgForOf,
    MessageModule,
    VirtualScrollerModule,
    AccordionModule,
    NgClass,
    ProgressSpinnerModule,
    ChipModule
  ],
  templateUrl: './pregled-predmeta.component.html',
  styleUrl: './pregled-predmeta.component.css'
})
export class PregledPredmetaComponent implements OnInit{

  loading: boolean = true;

  predmeti!:Predmet[]
  studentUsername:any


  studentNaGodini:StudentNaGodini[]=[]

  ngOnInit() {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.studentUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    if (this.studentUsername) {
      this.studentNaGodiniService.getAllByStudentUsername(this.studentUsername).subscribe({
        next: (data) => {
          this.studentNaGodini = data;
          console.log(data);
          this.loading = false;
        },
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Unable to fetch predmeti' });
          this.loading = false;
        }
      });
    } else {
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Invalid user token' });
      this.loading = false;
    }
  }

  constructor(private messageService: MessageService, private predmetService:PredmetService,private studentNaGodiniService: StudentNaGodiniService){

  }


}
