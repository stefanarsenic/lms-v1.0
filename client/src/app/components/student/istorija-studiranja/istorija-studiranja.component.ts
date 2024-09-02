import {Component, OnInit} from '@angular/core';
import {PanelModule} from "primeng/panel";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {IspitniRok} from "../../../model/ispitniRok";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {StudijskiProgram} from "../../../model/studijskiProgram";
import {Predmet} from "../../../model/predmet";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-istorija-studiranja',
  standalone: true,
  imports: [
    PanelModule,
    NgForOf
  ],
  templateUrl: './istorija-studiranja.component.html',
  styleUrl: './istorija-studiranja.component.css'
})
export class IstorijaStudiranjaComponent implements OnInit{

  studentUsername!: string;
  studentiNaGodini: StudentNaGodini[] = [];
  studijskiProgrami: StudijskiProgram[] = [];

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
      }
    });
  }

}
