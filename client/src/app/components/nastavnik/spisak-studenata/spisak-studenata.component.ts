import {Component, ViewChild} from '@angular/core';
import {Predmet} from "../../../model/predmet";
import {MessageService, PrimeTemplate} from "primeng/api";
import {PredmetService} from "../../../services/predmet.service";
import {Table, TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {Button} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {ToastModule} from "primeng/toast";
import {Student} from "../../../model/student";
import {ChipsModule} from "primeng/chips";
import {NgIf} from "@angular/common";
import {ProgressSpinnerModule} from "primeng/progressspinner";

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
    ToastModule,
    ChipsModule,
    NgIf,
    ProgressSpinnerModule
  ],
  templateUrl: './spisak-studenata.component.html',
  styleUrl: './spisak-studenata.component.css'
})


export class SpisakStudenataComponent {
  @ViewChild('dt') dt: Table | undefined;
  predmeti!: Predmet[];
  nastavnikUsername: any;
  PodaciTabela: PredmetStudenti[] = [];
  loading: boolean = true;

  constructor(private messageService: MessageService, private predmetService: PredmetService) {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
    }

    if (this.nastavnikUsername) {
      this.predmetService.getPredmetByNastavnik(this.nastavnikUsername).subscribe({
        next: (data) => {
          this.predmeti = data;
          this.PodaciTabela = []; // Ensure PodaciTabela is cleared before pushing new data
          this.loading = false;
          for (let predmet of data) {
            const predmetStudenti: PredmetStudenti = { predmet: predmet, studenti: [] };
            this.predmetService.getStudentiByPremdet(predmet.id).subscribe((r: Student[]) => {
              predmetStudenti.studenti = r;
              this.PodaciTabela.push(predmetStudenti); // Populate PodaciTabela here
              this.loading = false; // Ensure loading is false after the data has been loaded
            });
          }
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

  applyFilterGlobal(event: any) {
    this.dt?.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }
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
