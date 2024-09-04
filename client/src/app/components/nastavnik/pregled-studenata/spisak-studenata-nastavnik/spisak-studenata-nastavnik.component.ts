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

  informacijeStudenta: any = {};
  studenti: any[] = [];
  selectedStudent!: any;

  predmetId!: number;

  visible: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private studentNaGodiniService: StudentNaGodiniService,
    private polaganjeService: PolaganjeService
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

  prikaziPodatke(id: number){
    this.visible = true;
    if(id){
      this.selectedStudent = this.studenti.find(student => student.student.id === id);
      if(this.selectedStudent) {
        let params: HttpParams = new HttpParams()
          .set("studentId", this.selectedStudent.student.id);
        this.studentNaGodiniService.getStudentInfo(params).subscribe(data => this.informacijeStudenta = data);
        this.polaganjeService.getPrijavljeniIspitiStudenta(params).subscribe(data => {
          this.informacijeStudenta = {
            ...this.informacijeStudenta,
            upisi: data
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
