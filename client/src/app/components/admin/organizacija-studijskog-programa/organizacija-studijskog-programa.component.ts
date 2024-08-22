import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {DragDropModule} from "primeng/dragdrop";
import {PredmetService} from "../../../services/predmet.service";
import {CommonModule, NgForOf} from "@angular/common";
import {TableModule} from "primeng/table";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {Button} from "primeng/button";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {catchError, lastValueFrom} from "rxjs";
import {PlanZaGodinu} from "../../../model/planZaGodinu";
import {PlanZaGodinuService} from "../../../services/plan-za-godinu.service";
import {PredmetPlanaZaGodinuService} from "../../../services/predmet-plana-za-godinu.service";
import {PredmetPlanaZaGodinu} from "../../../model/predmetPlanaZaGodinu";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";

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
    ProgressSpinnerModule,
    NgForOf,
    ToastModule
  ],
  templateUrl: './organizacija-studijskog-programa.component.html',
  styleUrl: './organizacija-studijskog-programa.component.css'
})
export class OrganizacijaStudijskogProgramaComponent implements OnInit{

  constructor(
    private predmetService: PredmetService,
    private studijskiProgramService: StudijskiProgramService,
    private planZaGodinuService: PlanZaGodinuService,
    private predmetPlanaZaGodinuService: PredmetPlanaZaGodinuService,
    private messageService: MessageService
  ) {}

  predmetiStudijskogPrograma: any[] = [];
  sviPredmeti: any[] = [];
  predmeti: any[] = [];
  selektovaniPredmeti: any[] = [];
  prevuceniPredmet: any = {};

  studijskiProgrami: any[] = [];
  selektovaniStudijskiProgram: any = {};
  planoviZaGodinu: PlanZaGodinu[] = [];
  predmetiPlanaZaGodinu: PredmetPlanaZaGodinu[] = [];
  planZaGodinu!: PlanZaGodinu;
  semestri: number[] = [];
  loadingHidden: boolean = true;
  tableLoading: boolean = false;
  pzgLoading: boolean = false;

  ngOnInit(): void {
    this.selektovaniPredmeti = [];
    this.getPredmeti();
    this.getStudijskiProgrami();
  }
  pretrazi(){
    this.predmetService.getPredmetByStudijskiProgramAndGodina(this.selektovaniStudijskiProgram.id, this.planZaGodinu.godina).subscribe(
      data => {
        this.selektovaniPredmeti = data;
      });
  }
  proveriProgram(){
    this.semestri = [];
    if(this.selektovaniStudijskiProgram){
      this.pretrazi();
      if(this.planZaGodinu){
        this.loadingHidden = false;
        lastValueFrom(this.predmetPlanaZaGodinuService.getByPlanZaGodinu(this.planZaGodinu.id))
          .then((data) => {
            this.loadingHidden = true;
            this.predmetiPlanaZaGodinu = data
          })
          .catch((error) => {
            console.error(error);
          });
        for(let i: number = 1;i <= this.planZaGodinu.brojSemestara;i++){
          this.semestri.push(i);
        }
      }
    }
  }
  getfiltriraniPredmeti(semestar: number){
    return this.predmetiPlanaZaGodinu.filter(ppzg => ppzg.semestar === semestar);
  }
  getPredmeti() {
    this.tableLoading = true;
    lastValueFrom(this.predmetService.getAll())
      .then(data => {
        this.tableLoading = false;
        this.predmeti = data;
        this.sviPredmeti = data;
      })
      .catch((error) => {
        console.error(error);
      });
  }
  getStudijskiProgrami(){
    this.studijskiProgramService.getAll().subscribe(data => {
      this.studijskiProgrami = data;
    });
  }
  selectStudijskiProgram(){
    this.predmeti = this.sviPredmeti;
    this.planoviZaGodinu = [];
    this.pzgLoading = true;

    lastValueFrom(this.planZaGodinuService.findByStudijskiProgramId(this.selektovaniStudijskiProgram.id))
      .then(data => {
        this.pzgLoading = false;
        this.planoviZaGodinu = data
      })
      .catch((error) => console.error(error));

    if(this.planZaGodinu){
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
  drop(semestar: number) {
    for(let ppzg of this.predmetiPlanaZaGodinu) {
      if (this.prevuceniPredmet.id === ppzg.predmet.id) {
        this.prevuceniPredmet = null;
      }
    }
    if (this.prevuceniPredmet) {
        this.predmetiPlanaZaGodinu = [...(this.predmetiPlanaZaGodinu as any[]),
          {
            id: 0,
            predmet: this.prevuceniPredmet,
            semestar: semestar,
            planZaGodinu: this.planZaGodinu
          }
        ];
        this.predmeti = this.predmeti.filter((predmet) => predmet.id !== this.prevuceniPredmet.id);
        this.prevuceniPredmet = null;
    }
  }
  dragEnd() {
    this.prevuceniPredmet = null;
  }
  sacuvaj(){
    if(this.predmetiPlanaZaGodinu){
      this.loadingHidden = false;
      lastValueFrom(this.predmetPlanaZaGodinuService.createInBatch(this.planZaGodinu.id, this.predmetiPlanaZaGodinu))
        .then(() => {
          this.loadingHidden = true;
          this.proveriProgram();
          this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Predmeti uspesno izmenjeni', life: 1000 });
        })
        .catch((error) => {
          this.loadingHidden = true;
          this.proveriProgram();
          this.messageService.add({ severity: 'error', summary: 'Rejected', detail: error.message, life: 1000 });
        });
    }
  }
  removePredmet(ppzg: PredmetPlanaZaGodinu){
    const index = this.predmetiPlanaZaGodinu.findIndex(item =>
      item.id === ppzg.id
    );

    if(index > -1){
      this.predmeti.unshift(ppzg.predmet);
      this.predmetiPlanaZaGodinu.splice(index, 1);
    }
  }
  uporediPredmete(){
   this.predmeti = this.predmeti.filter(predmet1 =>
     !this.predmetiStudijskogPrograma.some(predmet2 => predmet2.id === predmet1.id)
   );
  }
}
