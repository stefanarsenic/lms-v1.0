import {Component, OnInit, ViewChild} from '@angular/core';
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {Table, TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {IconFieldModule} from "primeng/iconfield";
import {InputIconModule} from "primeng/inputicon";
import {ChipsModule} from "primeng/chips";
import {MultiSelectModule} from "primeng/multiselect";
import {FormsModule} from "@angular/forms";
import {DropdownModule} from "primeng/dropdown";
import {TagModule} from "primeng/tag";
import {NgClass, NgIf} from "@angular/common";
import {StudentService} from "../../../services/student.service";
import {ToastModule} from "primeng/toast";
import {Button, ButtonDirective} from "primeng/button";
import {MessageService} from "primeng/api";
import {Ripple} from "primeng/ripple";
import {forkJoin, lastValueFrom, of, tap,} from "rxjs";
import {parseAndFormatDate} from "../../../utils/datum-utils";
import {DialogModule} from "primeng/dialog";
import {AvatarModule} from "primeng/avatar";
import {StudijskiProgram} from "../../../model/studijskiProgram";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {PolozeniPredmetService} from "../../../services/polozeni-predmet.service";
import {PlanZaGodinuService} from "../../../services/plan-za-godinu.service";
import {formatDateFromString} from "../../../utils/date-converter";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-upis-studenata',
  standalone: true,
  imports: [
    TableModule,
    IconFieldModule,
    InputIconModule,
    ChipsModule,
    MultiSelectModule,
    FormsModule,
    DropdownModule,
    TagModule,
    NgClass,
    ToastModule,
    Button,
    Ripple,
    ButtonDirective,
    DialogModule,
    AvatarModule,
    NgIf
  ],
  templateUrl: './upis-studenata.component.html',
  styleUrl: './upis-studenata.component.css'
})
export class UpisStudenataComponent implements OnInit{

  studijskiProgrami: StudijskiProgram[] = [];
  dozvoljeniStudijskiProgrami: StudijskiProgram[] = [];
  selektovaniStudijskiProgram!: StudijskiProgram;
  studenti: any[] = [];
  selektovaniStudent: any = {};
  selektovaniStudentNaGodini!: any;
  upisDialog: boolean = false;
  upisNaSledecuGodinuDialog: boolean = false;
  hideSuccessMessage: boolean = true;
  hideWarningMessage: boolean = true;
  godina: number = new Date().getFullYear();
  brojIndeksa: number | null = null;
  uslovEspb: number = 0;
  visible: boolean = false;

  constructor(
    private studentNaGodiniService: StudentNaGodiniService,
    private studentService: StudentService,
    private messageService: MessageService,
    private studijskiProgramService: StudijskiProgramService,
    private polozeniPredmetService: PolozeniPredmetService,
    private planZaGodinuService: PlanZaGodinuService
  ) {}

  ngOnInit(): void {
    this.getStudenti();
    this.getStudijskiProgrami();
  }

  otkazi(){
    this.upisNaSledecuGodinuDialog = false;
  }

  upis(student: any) {
    this.selektovaniStudent = student;

    let params: HttpParams = new HttpParams()
      .set("studentId", this.selektovaniStudent.id)
    this.studijskiProgramService.getAllNotAttended(params).subscribe(data => {
      this.dozvoljeniStudijskiProgrami = data;
      this.visible = true;
      this.upisDialog = true;
    });
  }
  upisNaSledecuGodinu(studentNaGodini: StudentNaGodini){
    forkJoin({
      uslovEspb: this.planZaGodinuService.getUslovEspb(studentNaGodini.studijskiProgram.id, studentNaGodini.godinaStudija),
      ostvareniEspb: studentNaGodini.id ? this.polozeniPredmetService.getUkupnoEspbByStudentId(studentNaGodini.id) : of(null)
    }).pipe(
      tap(({ uslovEspb, ostvareniEspb }) => {
        this.uslovEspb = uslovEspb;
        if (studentNaGodini.id) {
          this.selektovaniStudentNaGodini = {
            ...studentNaGodini,
            ostvareniEspb: ostvareniEspb ? ostvareniEspb : 0
          };
        }

        if (this.selektovaniStudentNaGodini.ostvareniEspb < this.uslovEspb) {
          this.hideWarningMessage = false;
        } else {
          this.hideSuccessMessage = false;
        }

        this.upisNaSledecuGodinuDialog = true;
      })
    ).subscribe({
      error: (error) => {
        console.error('Error fetching data', error);
      }
    });
  }
  getStudijskiProgrami(){
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
  }
  async getStudenti() {
    try {
      const students = await lastValueFrom(this.studentService.getAll());

      const studentRequests = students.map(async student => {
        const studentiNaGodini = await lastValueFrom(this.studentNaGodiniService.getAllByStudentId(student.id));
        const parsedStudentiNaGodini = studentiNaGodini.map(sng => {
          if(sng.datumUpisa) {
            sng.datumUpisa = new Date(parseAndFormatDate(sng.datumUpisa.toString()));
          }
          return sng;
        })
        return {
          ...student,
          studentiNaGodini: parsedStudentiNaGodini
        };
      });

      this.studenti = await Promise.all(studentRequests);
    } catch (error) {
      console.error('Greska u dobavljanju studenata!', error);
    }
  }
  potvrdi(){
    this.studentNaGodiniService.update(this.selektovaniStudentNaGodini.id, this.selektovaniStudentNaGodini).subscribe({
          next: (response) => {
          this.messageService.add({ severity: 'success', summary: 'Transakcija uspesna!', life: 1000 });
          this.getStudenti()
          this.upisNaSledecuGodinuDialog = false;
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Transakcija neuspesna!', life: 1000 });
          this.upisNaSledecuGodinuDialog = false;
        }
      }
    );
  }
  sacuvaj(){
    if(this.selektovaniStudijskiProgram && this.brojIndeksa) {
      let studentNaGodini: StudentNaGodini = {
        id: null,
        datumUpisa: new Date(),
        datumZavrsetka: undefined,
        student: this.selektovaniStudent,
        studijskiProgram: this.selektovaniStudijskiProgram,
        brojIndeksa: this.godina.toString() + "/" + this.brojIndeksa?.toString(),
        godinaStudija: 1,
        predmeti:[]
      };

      this.studentNaGodiniService.create(studentNaGodini).subscribe({
        next: (response) => {
          this.messageService.add({ severity: 'success', summary: 'Transakcija uspesna!', life: 1000 });
          this.getStudenti();
          this.upisDialog = false;
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Transakcija neuspesna!', life: 1000 });
          this.upisDialog = false;
        }
      });
    }
    this.messageService.add({ severity: 'info', summary: 'Polja ne smeju biti prazna!', life: 1000 });
  }
  onRowExpand(event: TableRowExpandEvent) {
    this.messageService.add({ severity: 'info', summary: 'Student Expanded', detail: event.data.name, life: 1000 });
  }
  onRowCollapse(event: TableRowCollapseEvent) {
    this.messageService.add({ severity: 'success', summary: 'Student Collapsed', detail: event.data.name, life: 1000 });
  }

  protected readonly formatDateFromString = formatDateFromString;
  protected readonly parseAndFormatDate = parseAndFormatDate;
}
