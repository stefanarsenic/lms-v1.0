import {Component, OnInit} from '@angular/core';
import {TabViewModule} from "primeng/tabview";
import {NgForOf, NgIf} from "@angular/common";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {StudijskiProgram} from "../../../model/studijskiProgram";
import {HttpParams} from "@angular/common/http";
import {formatDateFromString} from "../../../utils/date-converter";
import {Button} from "primeng/button";
import {TableModule} from "primeng/table";
import {TabMenuModule} from "primeng/tabmenu";
import {PanelModule} from "primeng/panel";
import {DialogModule} from "primeng/dialog";
import {PredmetService} from "../../../services/predmet.service";
import {Predmet} from "../../../model/predmet";
import {DropdownModule} from "primeng/dropdown";
import {IspitniRok} from "../../../model/ispitniRok";
import {IspitniRokService} from "../../../services/ispitni-rok.service";
import {FormsModule} from "@angular/forms";
import {PolaganjeService} from "../../../services/polaganje.service";
import {Polaganje} from "../../../model/polaganje";
import {EvaluacijaZnanjaService} from "../../../services/evaluacija-znanja.service";

@Component({
  selector: 'app-prijava-ispita',
  standalone: true,
  imports: [
    TabViewModule,
    NgForOf,
    Button,
    TableModule,
    NgIf,
    TabMenuModule,
    PanelModule,
    DialogModule,
    DropdownModule,
    FormsModule
  ],
  templateUrl: './prijava-ispita.component.html',
  styleUrl: './prijava-ispita.component.css'
})
export class PrijavaIspitaComponent implements OnInit{

  studentUsername!: string;
  ispitniRokovi: IspitniRok[] = [];
  selectedIspitniRok!: IspitniRok;

  studentiNaGodini: StudentNaGodini[] = [];
  studijskiProgrami: StudijskiProgram[] = [];
  studijskiProgramiIspiti: { [key: number]: Predmet[] } = {};

  selectedPredmet!: Predmet;

  visible: boolean = false;

  constructor(
    private studijskiProgramService: StudijskiProgramService,
    private studentNaGodiniService: StudentNaGodiniService,
    private predmetService: PredmetService,
    private ispitniRokService: IspitniRokService,
    private polaganjeService: PolaganjeService,
    private evaluacijaZnanjaService: EvaluacijaZnanjaService
  ) {
  }

  ngOnInit(): void {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.studentUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    this.studentNaGodiniService.getAllByStudentUsername(this.studentUsername).subscribe((data) => {
      this.studentiNaGodini = data;
      for(let sng of data){
        this.studijskiProgrami.push(sng.studijskiProgram);
        this.fetchPredmeti(sng.studijskiProgram.id);
      }
    });
    this.ispitniRokService.getAll().subscribe((data) => {
      console.log(data);
      let today = new Date();
      let twoWeeksAgo = new Date();

      for(let ispitniRok of data){
        twoWeeksAgo.setDate(new Date(ispitniRok.pocetak).getDate() - 14);

        if(today >= twoWeeksAgo && today < new Date(ispitniRok.pocetak)){
          this.ispitniRokovi.push(ispitniRok);
        }
      }
    });
  }

  potvrdiPrijavu(){

    // let polaganje: Polaganje = {
    //   id: 0,
    //   student: this.studentiNaGodini,
    //   bodovi: null,
    //   napomena: null,
    //   evaluacijaZnanja:
    // }
  }

  otkaziPrijavu(){
    this.visible = false;
  }

  prijaviIspit(predmet: Predmet){
    this.selectedPredmet = predmet;
    this.visible = true;
  }

  fetchPredmeti(studijskiProgramId: number): void {
    const student: any = this.studentiNaGodini.find(s => s.studijskiProgram.id === studijskiProgramId);

    if (student) {
      const params: HttpParams = new HttpParams()
        .set('studentId', student.id)
        .set('studijskiProgramId', studijskiProgramId);

      this.predmetService.getNepolozeniPredmeti(params).subscribe(data => {
        this.studijskiProgramiIspiti[studijskiProgramId] = data;
      });
    } else {
      this.studijskiProgramiIspiti[studijskiProgramId] = [];
    }
  }

  getPredmeti(studijskiProgramId: number): Predmet[] {
    return this.studijskiProgramiIspiti[studijskiProgramId] || [];
  }

  protected readonly formatDateFromString = formatDateFromString;
}
