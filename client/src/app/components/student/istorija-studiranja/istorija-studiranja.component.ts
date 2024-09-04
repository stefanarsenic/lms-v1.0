import {Component, OnInit} from '@angular/core';
import {PanelModule} from "primeng/panel";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {IspitniRok} from "../../../model/ispitniRok";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {StudijskiProgram} from "../../../model/studijskiProgram";
import {Predmet} from "../../../model/predmet";
import {NgForOf} from "@angular/common";
import {HttpParams} from "@angular/common/http";
import {TableModule} from "primeng/table";

@Component({
  selector: 'app-istorija-studiranja',
  standalone: true,
  imports: [
    PanelModule,
    NgForOf,
    TableModule
  ],
  templateUrl: './istorija-studiranja.component.html',
  styleUrl: './istorija-studiranja.component.css'
})
export class IstorijaStudiranjaComponent implements OnInit{

  studentUsername!: string;
  studentiNaGodini: StudentNaGodini[] = [];
  studijskiProgrami: StudijskiProgram[] = [];

  informacijeStudenata: {[studentId: number]: any} = {};

  constructor(
    private studijskiProgramService: StudijskiProgramService,
    private studentNaGodiniService: StudentNaGodiniService,
  ) {}

  ngOnInit(): void {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.studentUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    this.studentNaGodiniService.getAllByStudentUsername(this.studentUsername).subscribe((data) => {
      this.studentiNaGodini = data;
      for(let sng of data){
        this.studijskiProgrami.push(sng.studijskiProgram);

        if(sng.id != null) {
          let params: HttpParams = new HttpParams()
            .set("studentId", sng.id);
          this.studentNaGodiniService.getStudentInfo(params).subscribe(data => {
            this.informacijeStudenata[sng.studijskiProgram.id] = data;
          })
        }
      }
    });
  }

  getInformacijeStudenata(studijskiProgramId: number){
    return this.informacijeStudenata[studijskiProgramId] || [];
  }

}
