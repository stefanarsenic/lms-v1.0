import { Component } from '@angular/core';
import {Predmet} from "../../../model/predmet";
import {MessageService, PrimeTemplate} from "primeng/api";
import {PredmetService} from "../../../services/predmet.service";
import {TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {Button} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {ToastModule} from "primeng/toast";
import {Student} from "../../../model/student";

interface PredmetStudenti {
  predmet: Predmet;
  studenti: Student[];
}
@Component({
  selector: 'app-spisak-studenata',
  standalone: true,
  imports: [
    Button,
    PrimeTemplate,
    Ripple,
    TableModule,
    ToastModule
  ],
  templateUrl: './spisak-studenata.component.html',
  styleUrl: './spisak-studenata.component.css'
})


export class SpisakStudenataComponent {

  predmeti!: Predmet[];
  nastavnikUsername: any;
  PodaciTabela: PredmetStudenti[] = [];
  loading: boolean = true;

  constructor(private messageService: MessageService, private predmetService: PredmetService) {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    console.log(this.nastavnikUsername);
    if (this.nastavnikUsername) {
      this.predmetService.getPredmetByNastavnik(this.nastavnikUsername).subscribe({
        next: (data) => {
          this.predmeti = data;
          this.loading = false;
          for (let predmet of data) {
            const predmetStudenti: PredmetStudenti = { predmet: predmet, studenti: [] };
            this.predmetService.getStudentiByPremdet(predmet.id).subscribe((r: Student[]) => {
              predmetStudenti.studenti = r;
              this.PodaciTabela.push(predmetStudenti);
            });
          }
          console.log(this.PodaciTabela);
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

  expandedRows: { [key: string]: boolean } = {};

  expandAll() {
    this.expandedRows = this.PodaciTabela.reduce((acc, p) => {
      acc[p.predmet.id] = true;
      return acc;
    }, {} as { [key: string]: boolean });
  }

  collapseAll() {
    this.expandedRows = {};
  }

  onRowExpand(event: TableRowExpandEvent) {
  }

  onRowCollapse(event: TableRowCollapseEvent) {
  }

}
