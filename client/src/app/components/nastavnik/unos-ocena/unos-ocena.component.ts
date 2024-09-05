import {Component, OnInit} from '@angular/core';
import {DropdownModule} from "primeng/dropdown";
import {Predmet} from "../../../model/predmet";
import {PredmetService} from "../../../services/predmet.service";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import {HttpParams} from "@angular/common/http";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {PolaganjeService} from "../../../services/polaganje.service";
import {TableModule} from "primeng/table";
import {Button, ButtonDirective} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {Polaganje} from "../../../model/polaganje";
import {DialogModule} from "primeng/dialog";
import {PaginatorModule} from "primeng/paginator";
import {PolaganjePredmeta} from "../../../model/polaganjePredmeta";
import {PolaganjePredmetaService} from "../../../services/polaganje-predmeta.service";
import {NgIf} from "@angular/common";
import {EvaluacijaZnanjaService} from "../../../services/evaluacija-znanja.service";
import {EvaluacijaZnanja} from "../../../model/evaluacijaZnanja";
import {TipPolaganja} from "../../../model/tipPolaganja";
import {formatDateFromString} from "../../../utils/date-converter";
import {parseAndFormatDate} from "../../../utils/datum-utils";

@Component({
  selector: 'app-unos-ocena',
  standalone: true,
  imports: [
    DropdownModule,
    ToastModule,
    FormsModule,
    TableModule,
    ButtonDirective,
    Ripple,
    Button,
    DialogModule,
    PaginatorModule,
    NgIf
  ],
  templateUrl: './unos-ocena.component.html',
  styleUrl: './unos-ocena.component.css'
})
export class UnosOcenaComponent implements OnInit{

  predmeti: Predmet[] = [];
  selectedPredmet!: Predmet;

  studenti: any[] = [];

  nastavnikUsername!: string;

  loading: boolean = false;
  loadingTabele: boolean = false;

  prijaveIspitaStudenta: any[] = [];

  aktivnostiStudenta: Polaganje[] = [];

  showPregledAktivnosti: boolean = false;
  showUnosBodova: boolean = false;
  showDodajPolaganje: boolean = false;

  studentId!: number;
  polaganjeId!: number;
  bodovi: number = 0;
  napomena!: string;

  showWarningDialog: boolean = false;
  warningMessage: string = "";

  nepolaganiKolokvijumi: EvaluacijaZnanja[] = [];
  selectedKolokvijum!: EvaluacijaZnanja;

  tipoviPolaganja: TipPolaganja[] = [];
  selectedTipPolaganja!: TipPolaganja;

  loadingKolokvijuma: boolean = false;
  bodoviKolokvijuma: number = 0;

  constructor(
    private predmetService: PredmetService,
    private messageService: MessageService,
    private studentNaGodiniService: StudentNaGodiniService,
    private polaganjeService: PolaganjeService,
    private polaganjePredmetaService: PolaganjePredmetaService,
    private evaluacijaZnanjaService: EvaluacijaZnanjaService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
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

  getKolokvijumi(){
    this.loadingKolokvijuma = true;
    this.showDodajPolaganje = true;
    let params: HttpParams = new HttpParams()
      .set("predmetId", this.selectedPredmet.id)
      .set("studentId", this.studentId);
    this.evaluacijaZnanjaService.getNepolaganiKolokvijumiByPredmetAndStudent(params)
      .subscribe(data => {
        this.nepolaganiKolokvijumi = data;
        this.loadingKolokvijuma = false;
      });
    this.evaluacijaZnanjaService.getTipoviPolaganja().subscribe(data => this.tipoviPolaganja = data);
  }

  getOcena(){
    let sum: number = 0;
    for(let polaganje of this.aktivnostiStudenta){
      sum += polaganje.bodovi? polaganje.bodovi :  0;
    }

    return Math.round(sum/10);
  }

  getStudenti(){
    this.reset();
    this.loadingTabele = true;
    let p: HttpParams = new HttpParams()
      .set("predmetId", this.selectedPredmet.id);
    this.studentNaGodiniService.getStudentiByPredmet(p).subscribe((data) => {
      this.studenti = data;
      if(data){
        for(let student of data){
          let p: HttpParams = new HttpParams()
            .set("studentId", student.student.id);
          this.polaganjeService.getPrijavljeniIspitiStudenta(p).subscribe(data => {
            data = data.filter(p => p.predmet.id === this.selectedPredmet.id);
            this.prijaveIspitaStudenta.push({
              student: student.student,
              prijaveIspita: data
            });
          });
        }
        this.loadingTabele = false;
      }
    });
  }

  sacuvajPolaganjeKolokvijuma(){
    if(this.studentId && this.selectedPredmet && this.selectedTipPolaganja && this.selectedKolokvijum) {
      let params: HttpParams = new HttpParams()
        .set("studentId", this.studentId)
        .set("predmetId", this.selectedPredmet.id)
        .set("tipPolaganjaId", this.selectedTipPolaganja.id)
        .set("evaluacijaZnanjaId", this.selectedKolokvijum.id);

      this.polaganjeService.createPolaganjeKolokvijuma(params, {bodovi: this.bodoviKolokvijuma}).subscribe({
        next: () => {
          this.showDodajPolaganje = false;
          this.bodoviKolokvijuma = 0;
          this.pregledAktivnosti(this.studentId);
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Kreiranje polaganja uspesno",
            life: 1000
          });
        },
        error: (err) => {
          const errorMsg = err.error?.message || "Unexpected error occurred";
          this.messageService.add({severity: "error", summary: "Error", detail: errorMsg, life: 2000});
        }
      });
    }
    else {
      this.warningMessage = "Uneti podaci nisu validni";
      this.showWarningDialog = true;
    }
  }

  sacuvaj(){
    if(this.bodovi) {
      let params: HttpParams = new HttpParams()
        .set("polaganjeId", this.polaganjeId)

      this.polaganjeService.updatePolaganje(params, {bodovi: this.bodovi, napomena: this.napomena})
        .subscribe({
          next: () => {
            this.showUnosBodova = false;
            this.reset();
            this.getStudenti();
            this.messageService.add({
              severity: "success",
              summary: "Success",
              detail: "Azuriranje polaganja uspesno",
              life: 1000
            });
          },
          error: (err: any) => {
            const errorMsg = err.error?.message || "Unexpected error occurred";
            this.messageService.add({severity: "error", summary: "Error", detail: errorMsg, life: 2000});
          }
        });
    }
    else {
      this.messageService.add({severity: "error", summary: "Error", detail: "Bodovi ne smeju biti prazni", life: 2000});
    }
  }

  closeWarningDialog(){
    this.warningMessage = "";
    this.showWarningDialog = false;
  }

  reset(){
    this.bodovi = 0;
    this.napomena = "";
    this.prijaveIspitaStudenta = [];
    this.studenti = [];
  }

  upisiOcenu() {
    if(this.aktivnostiStudenta.length === 3) {
      let params: HttpParams = new HttpParams()
        .set("studentId", this.studentId)
        .set("predmetId", this.selectedPredmet.id);

      let polaganjePredmeta: PolaganjePredmeta = {
        id: 0,
        konacnaOcena: this.getOcena(),
        polaganja: this.aktivnostiStudenta
      }

      this.polaganjePredmetaService.createPolaganje(params, polaganjePredmeta).subscribe({
        next: () => {
          this.showPregledAktivnosti = false;
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Polaganje uspesno arhivirano",
            life: 1000
          });
        },
        error: (err) => {
          const errorMsg = err.error?.message || "Unexpected error occurred";
          console.error(errorMsg);
          this.messageService.add({severity: "error", summary: "Error", detail: errorMsg, life: 2000});        }
      });
    }
    else{
      this.warningMessage = "Student nije ispunio sve obaveze";
      this.showWarningDialog = true;
    }
  }

  upisiBodove(polaganjeId: number){
    this.polaganjeId = polaganjeId;
    this.showUnosBodova = true;
  }

  pregledAktivnosti(studentId: number){
    this.studentId = studentId;
    let params: HttpParams = new HttpParams()
      .set("studentId", studentId)
      .set("predmetId", this.selectedPredmet.id);
    this.polaganjeService.getLatestPolaganjaByStudent(params).subscribe(data =>
    {
      this.aktivnostiStudenta = data;
      this.showPregledAktivnosti = true;
    });
  }

  protected readonly formatDateFromString = formatDateFromString;
  protected readonly parseAndFormatDate = parseAndFormatDate;
}
