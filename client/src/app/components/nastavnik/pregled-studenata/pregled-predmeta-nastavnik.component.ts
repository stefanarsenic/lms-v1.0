import {Component, OnInit} from '@angular/core';
import {Predmet} from "../../../model/predmet";
import {PredmetService} from "../../../services/predmet.service";
import {MessageService} from "primeng/api";
import {CardModule} from "primeng/card";
import {NgForOf} from "@angular/common";
import {Router, RouterLink, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-pregled-studenata',
  standalone: true,
  imports: [
    CardModule,
    NgForOf,
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './pregled-predmeta-nastavnik.component.html',
  styleUrl: './pregled-predmeta-nastavnik.component.css'
})
export class PregledPredmetaNastavnikComponent{

}
