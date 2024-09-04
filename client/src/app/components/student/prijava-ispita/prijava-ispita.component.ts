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
import {parseAndFormatDate} from "../../../utils/datum-utils";
import {lastValueFrom} from "rxjs";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";

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
    FormsModule,
    ToastModule
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
  student!: StudentNaGodini | undefined;

  prijavljeniIspiti: { [key: number]: any[]} = {};

  studijskiProgramId!: number;

  visible: boolean = false;

  constructor(
    private studijskiProgramService: StudijskiProgramService,
    private studentNaGodiniService: StudentNaGodiniService,
    private predmetService: PredmetService,
    private ispitniRokService: IspitniRokService,
    private polaganjeService: PolaganjeService,
    private evaluacijaZnanjaService: EvaluacijaZnanjaService,
    private messageService: MessageService
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
        this.fetchPrijavljeniIspiti(sng.studijskiProgram.id);
      }
    });
    this.ispitniRokService.getAll().subscribe((data) => {
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

  async createPolaganjeIspita(params: HttpParams): Promise<any>{
    try{
      return lastValueFrom(this.polaganjeService.createPolaganjeIspita(params));
    }
    catch (error) {
      console.error(error);
    }
  }

  potvrdiPrijavu(){
    if(this.student && this.student.id && this.selectedPredmet && this.selectedIspitniRok) {
      let params: HttpParams = new HttpParams()
        .set("studentId", this.student.id)
        .set("predmetId", this.selectedPredmet.id)
        .set("ispitniRokId", this.selectedIspitniRok.id);

      this.createPolaganjeIspita(params)
        .then(data => {
          this.fetchPrijavljeniIspiti(this.studijskiProgramId);
          this.messageService.add({severity: "success", summary: "Success", detail: "Prijava ispita uspesno izvrsena", life: 1000});
          this.visible = false;
        })
        .catch(error => {
          this.messageService.add({severity: "error", summary: "Error", detail: "Greska prilikom prijave ispita", life: 1000});
        });
    }
    else {
      this.messageService.add({severity: "error", summary: "Error", detail: "Greska prilikom prijave ispita", life: 1000});
    }
  }

  fetchPrijavljeniIspiti(studijskiProgramId: number){
    const student = this.studentiNaGodini.find(s => s.studijskiProgram.id === studijskiProgramId);

    if(student && student.id){
      let params: HttpParams = new HttpParams()
        .set("studentId", student.id);

      this.polaganjeService.getPrijavljeniIspitiStudenta(params).subscribe(data => {
        this.prijavljeniIspiti[studijskiProgramId] = data;
      });
    }
    else{
      this.prijavljeniIspiti[studijskiProgramId] = [];
    }

  }

  getPrijavljeniIspiti(studijskiProgramId: number): any[]{
    return this.prijavljeniIspiti[studijskiProgramId] || [];
  }

  otkaziPrijavu(){
    this.visible = false;
  }

  prijaviIspit(studijskiProgramId: number, predmet: Predmet){
    this.studijskiProgramId = studijskiProgramId;
    this.student = this.studentiNaGodini.find(s => s.studijskiProgram.id === studijskiProgramId);
    this.selectedPredmet = predmet;
    this.visible = true;
  }

  fetchPredmeti(studijskiProgramId: number): void {
    const student: any = this.studentiNaGodini.find(s => s.studijskiProgram.id === studijskiProgramId);

    if (student) {
      const params: HttpParams = new HttpParams()
        .set('studentId', student.id)

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
