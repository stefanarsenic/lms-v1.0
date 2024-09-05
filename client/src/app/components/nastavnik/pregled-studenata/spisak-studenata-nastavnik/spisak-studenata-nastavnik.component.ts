import {Component, OnInit} from '@angular/core';
import {Predmet} from "../../../../model/predmet";
import {HttpParams} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {StudentNaGodiniService} from "../../../../services/student-na-godini.service";
import {PredmetService} from "../../../../services/predmet.service";
import {StudentNaGodini} from "../../../../model/studentNaGodini";
import {Student} from "../../../../model/student";
import {TableModule} from "primeng/table";
import {ChipsModule} from "primeng/chips";
import {Button} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {AccordionModule} from "primeng/accordion";
import {NgIf} from "@angular/common";
import {formatDateFromString} from "../../../../utils/date-converter";
import {parseAndFormatDate} from "../../../../utils/datum-utils";
import {PanelModule} from "primeng/panel";
import {PolaganjeService} from "../../../../services/polaganje.service";
import {switchMap} from "rxjs";

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
    PanelModule
  ],
  templateUrl: './spisak-studenata-nastavnik.component.html',
  styleUrl: './spisak-studenata-nastavnik.component.css'
})
export class SpisakStudenataNastavnikComponent implements OnInit{

  prijavljeniIspiti: any[] = [];
  informacijeStudenta: any = {};
  studenti: any[] = [];
  selectedStudent!: any;

  predmetId!: number;

  visible: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private studentNaGodiniService: StudentNaGodiniService,
    private polaganjeService: PolaganjeService,
    private upisService: PolaganjeService
  ) {
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const id = params['id'];
      this.predmetId = id;
      let p: HttpParams = new HttpParams()
        .set("predmetId", id);
      this.studentNaGodiniService.getStudentiByPredmet(p).subscribe((data) => {
        this.studenti = data;
      });
    });
  }
//TODO: filteri za tabelu, unos ocene, instrumenti evaluacije, izolovati komponentu
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
