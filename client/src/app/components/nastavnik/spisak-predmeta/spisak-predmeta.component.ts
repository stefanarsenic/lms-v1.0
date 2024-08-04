import {Component, Injector, ViewChild} from '@angular/core';
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {Predmet} from "../../../model/predmet";
import {ConfirmationService, MessageService} from "primeng/api";
import {PredmetService} from "../../../services/predmet.service";
import {Table, TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {Button} from "primeng/button";
import {Ripple} from "primeng/ripple";

@Component({
  selector: 'app-spisak-predmeta',
  standalone: true,
  imports: [
    ToastModule,
    TableModule,
    Button,
    Ripple

  ],
  templateUrl: './spisak-predmeta.component.html',
  styleUrl: './spisak-predmeta.component.css'
})
export class SpisakPredmetaComponent{
  loading: boolean = true;

  predmeti!:Predmet[]
  nastavnikUsername:any
  predmetDetalji:any=[]

  constructor(private messageService: MessageService, private predmetService:PredmetService
  ) {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    console.log(this.nastavnikUsername)
    if (this.nastavnikUsername) {
      this.predmetService.getPredmetByNastavnik(this.nastavnikUsername).subscribe({
        next: (data) => {
          this.predmeti = data;
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

  expandedRows = {};


  expandAll() {
    this.expandedRows = this.predmeti.reduce((acc, p) => {
      acc[p.id] = true;
      return acc;
    }, {} as { [key: number]: boolean });
  }

  collapseAll() {
    this.expandedRows = {};
  }



  onRowExpand(event: TableRowExpandEvent) {
    this.predmetDetalji.push({"brojPredavanja":event.data.brojPredavanja,"brojVezbi":event.data.brojVezbi})
    this.messageService.add({ severity: 'info', summary: 'Product Expanded', detail: event.data.name, life: 3000 });
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    this.predmetDetalji=[]
    this.messageService.add({ severity: 'success', summary: 'Product Collapsed', detail: event.data.name, life: 3000 });
  }

  protected readonly Object = Object;
}
