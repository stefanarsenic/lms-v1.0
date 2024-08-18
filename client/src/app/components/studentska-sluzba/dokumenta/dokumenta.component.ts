import {Component, OnInit, ViewChild} from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import {Table, TableModule} from "primeng/table";
import {Button} from "primeng/button";
import {ChipsModule} from "primeng/chips";
import {FormsModule} from "@angular/forms";
import {Student} from "../../../model/student";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {TabelaStudenataComponent} from "./tabela-studenata/tabela-studenata.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-dokumenta',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    TableModule,
    Button,
    ChipsModule,
    FormsModule,
    TabelaStudenataComponent,
    NgForOf
  ],
  templateUrl: './dokumenta.component.html',
  styleUrl: './dokumenta.component.css'
})
export class DokumentaComponent{

}
