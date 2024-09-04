import {Component, OnInit} from '@angular/core';
import {CardModule} from "primeng/card";
import {NgForOf} from "@angular/common";
import {MessageService, PrimeTemplate} from "primeng/api";
import {Predmet} from "../../../../model/predmet";
import {PredmetService} from "../../../../services/predmet.service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-kartice-predmeta',
  standalone: true,
  imports: [
    CardModule,
    NgForOf,
    PrimeTemplate,
    RouterLink
  ],
  templateUrl: './kartice-predmeta.component.html',
  styleUrl: './kartice-predmeta.component.css'
})
export class KarticePredmetaComponent implements OnInit{

  nastavnikUsername!: string;
  predmeti: Predmet[] = [];

  loading: boolean = false;
  hidden: boolean = false;

  constructor(
    private predmetService: PredmetService,
    private messageService: MessageService,
  ) {
  }

  ngOnInit(): void {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
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
}
