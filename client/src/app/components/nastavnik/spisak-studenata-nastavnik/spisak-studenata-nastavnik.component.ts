import {Component, OnInit} from '@angular/core';
import {Predmet} from "../../../model/predmet";
import {HttpParams} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {PredmetService} from "../../../services/predmet.service";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {Student} from "../../../model/student";
import {TableModule} from "primeng/table";
import {ChipsModule} from "primeng/chips";
import {Button} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {AccordionModule} from "primeng/accordion";
import {NgIf} from "@angular/common";
import {formatDateFromString} from "../../../utils/date-converter";
import {parseAndFormatDate} from "../../../utils/datum-utils";
import {PanelModule} from "primeng/panel";
import {PolaganjeService} from "../../../services/polaganje.service";
import {switchMap} from "rxjs";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-spisak-studenata-nastavnik',
  standalone: true,
  imports: [
    TableModule,
    ChipsModule,
    Button,
    DialogModule,
    AccordionModule,
    NgIf,
    PanelModule,
    DropdownModule,
    FormsModule
  ],
  templateUrl: './spisak-studenata-nastavnik.component.html',
  styleUrl: './spisak-studenata-nastavnik.component.css'
})
export class SpisakStudenataNastavnikComponent implements OnInit{

  prijavljeniIspiti: any[] = [];
  informacijeStudenta: any = {};
  studenti: any[] = [];
  selectedStudent!: any;

  nastavnikUsername!: string;

  predmeti: Predmet[] = [];
  selectedPredmet!: Predmet;
  predmetId!: number;

  visible: boolean = false;
  loading: boolean = false;
  loadingTabele: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private studentNaGodiniService: StudentNaGodiniService,
    private polaganjeService: PolaganjeService,
    private predmetService: PredmetService,
    private messageService: MessageService,
    private upisService: PolaganjeService
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

  getStudenti(){
    this.loadingTabele = true;
    let params: HttpParams = new HttpParams()
      .set("predmetId", this.selectedPredmet.id)
    this.studentNaGodiniService.getStudentiByPredmet(params).subscribe((data) => {
      this.studenti = data;
      this.loadingTabele = false;
    });
  }

  prikaziPodatke(id: number){
    this.visible = true;
    if(id){
      this.selectedStudent = this.studenti.find(student => student.student.id === id);
      if(this.selectedStudent) {
        let params: HttpParams = new HttpParams()
          .set("studentId", this.selectedStudent.student.id);

        this.polaganjeService.getPrijavljeniIspitiStudenta(params).subscribe(data => this.prijavljeniIspiti = data);
        this.studentNaGodiniService.getStudentInfo(params)
          .pipe(
            switchMap((data) => {
              this.informacijeStudenta = data;
              return this.studentNaGodiniService.getUpisi(params);
            })
          )
          .subscribe({
            next: (upisiData) => {
              this.informacijeStudenta = {
                ...this.informacijeStudenta,
                upisi: upisiData
              };
              console.log(this.informacijeStudenta);
            },
            error: (error) => {
              console.error("Error occurred:", error);
            },
            complete: () => {
              console.log("Request completed.");
            }
          });
      }
    }
  }

  closeDialog(){
    this.visible = false;
  }

  protected readonly formatDateFromString = formatDateFromString;
  protected readonly parseAndFormatDate = parseAndFormatDate;
}
