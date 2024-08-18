import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {DragDropModule} from "primeng/dragdrop";
import {PredmetService} from "../../../services/predmet.service";
import {CommonModule} from "@angular/common";
import {TableModule} from "primeng/table";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {Button} from "primeng/button";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {lastValueFrom} from "rxjs";

@Component({
  selector: 'app-organizacija-studijskog-programa',
  standalone: true,
  imports: [
    DragDropModule,
    CommonModule,
    TableModule,
    DropdownModule,
    FormsModule,
    Button,
    ProgressSpinnerModule
  ],
  templateUrl: './organizacija-studijskog-programa.component.html',
  styleUrl: './organizacija-studijskog-programa.component.css'
})
export class OrganizacijaStudijskogProgramaComponent implements OnInit{

  constructor(
    private predmetService: PredmetService,
    private studijskiProgramService: StudijskiProgramService
  ) {}

  predmetiStudijskogPrograma: any[] = [];
  sviPredmeti: any[] = [];
  predmeti: any[] = [];
  selektovaniPredmeti: any[] = [];
  prevuceniPredmet: any = {};

  studijskiProgrami: any[] = [];
  selektovaniStudijskiProgram: any = {};

  godine: number[] = [];
  godina: number = 0;

  ngOnInit(): void {
    this.selektovaniPredmeti = [];
    this.getPredmeti();
    this.getStudijskiProgrami();
  }
  pretrazi(){
    this.predmetService.getPredmetByStudijskiProgramAndGodina(this.selektovaniStudijskiProgram.id, this.godina).subscribe(
      data => {
        this.selektovaniPredmeti = data;
      });
  }
  proveriProgram(){
    if(this.selektovaniStudijskiProgram){
      this.pretrazi();
    }
  }
  getPredmeti() {
    this.predmetService.getAll().subscribe(data => {
      this.predmeti = data;
      this.sviPredmeti = data;
    });
  }
  getStudijskiProgrami(){
    this.studijskiProgramService.getAll().subscribe(data => {
      this.studijskiProgrami = data;
    });
  }
  selectStudijskiProgram(){
    this.predmeti = this.sviPredmeti;
    this.godine = [];
    for(let i = 1; i <= this.selektovaniStudijskiProgram.godineTrajanja; i++){
      this.godine.push(i);
    }
    if(this.godina){
      this.pretrazi();
    }
    if(this.selektovaniStudijskiProgram) {
      this.predmetService.getPredmetiByStudijskiProgram(this.selektovaniStudijskiProgram.id).subscribe(data => {
        this.predmetiStudijskogPrograma = data;
        this.uporediPredmete();
      });
    }
  }
  dragStart(predmet: any) {
    this.prevuceniPredmet = predmet;
  }
  drop() {
    for(let predmet of this.selektovaniPredmeti) {
      if (this.prevuceniPredmet.id === predmet.id) {
        this.prevuceniPredmet = null;
      }
    }
    if (this.prevuceniPredmet) {
        this.selektovaniPredmeti = [...(this.selektovaniPredmeti as any[]), this.prevuceniPredmet];
        this.predmeti = this.predmeti.filter((predmet) => predmet.id !== this.prevuceniPredmet.id);
        this.prevuceniPredmet = null;
    }
  }
  dragEnd() {
    this.prevuceniPredmet = null;
  }
  dodajPlanSaPredmetima(){
    if(this.selektovaniPredmeti){
      this.predmetService.createPlanWithPredmeti(this.selektovaniPredmeti, this.selektovaniStudijskiProgram.id, this.godina).subscribe();
    }
  }

  uporediPredmete(){
   this.predmeti = this.predmeti.filter(predmet1 =>
     !this.predmetiStudijskogPrograma.some(predmet2 => predmet2.id === predmet1.id)
   );
  }
}
