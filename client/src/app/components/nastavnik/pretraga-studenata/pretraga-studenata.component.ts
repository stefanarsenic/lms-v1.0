import {Component, Injector, OnInit, ViewChild} from '@angular/core';
import {Button} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {DividerModule} from "primeng/divider";
import {DropdownModule} from "primeng/dropdown";
import {FileUploadModule} from "primeng/fileupload";
import {FormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {NgIf} from "@angular/common";
import {PasswordModule} from "primeng/password";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {Table, TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {PolozeniPredmetService} from "../../../services/polozeni-predmet.service";
import {formatDateFromString} from "../../../utils/date-converter";
import {parseAndFormatDate} from "../../../utils/datum-utils";


@Component({
  selector: 'app-pretraga-studenata',
  standalone: true,
  imports: [
    Button,
    CalendarModule,
    ConfirmDialogModule,
    DialogModule,
    DividerModule,
    DropdownModule,
    FileUploadModule,
    FormsModule,
    InputTextModule,
    NgIf,
    PasswordModule,
    PrimeTemplate,
    TableModule,
    ToastModule,
    ToolbarModule
  ],
  templateUrl: './pretraga-studenata.component.html',
  styleUrl: './pretraga-studenata.component.css'
})
export class PretragaStudenataComponent implements OnInit {

  @ViewChild('dt') dt: Table | undefined;
  loading: boolean = true;
  studijskIprogrami:StudentNaGodini[]=[]
  datum!:any
  prosecneOcene: { [studentId: number]: number } = {};

  constructor(private messageService: MessageService, private confirmationService: ConfirmationService,
             private  studijskiProgram:StudentNaGodiniService,private polozenPredmetService:PolozeniPredmetService) {

  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  ngOnInit(): void {
   this.studijskiProgram.getAll().subscribe((r:StudentNaGodini[])=>{
      this.studijskIprogrami=r;
     this.studijskIprogrami.map(s=>{
       s.datumUpisa=<Date>this.convertDate(s.datumUpisa)
     })
      this.loadAverageGrades();
    })
  }

  convertDate(input: any): Date | null {
    if (Array.isArray(input)) {
      const [year, month, day, hours, minutes] = input;
      return new Date(year, month - 1, day, hours, minutes);
    }

    if (typeof input === 'string') {
      const sanitizedDate = input.split('.')[0];
      return new Date(sanitizedDate.replace(' ', 'T'));
    }

    return null;
  }

  loadAverageGrades() {
    this.studijskIprogrami.forEach(student => {
      if(student.id){
        this.polozenPredmetService.getProsecnaOcenaByStudentId(student.id).subscribe(averageGrade => {
          this.prosecneOcene[student.student.id] = averageGrade;
        });
      }
    });
  }

  formatDateArray(dateArray: number[]): string {
    if (!dateArray || dateArray.length < 3) {
      return ''; // Return empty string if date is invalid
    }

    const year = dateArray[0];
    const month = dateArray[1];
    const day = dateArray[2];
    const date = new Date(year, month - 1, day);

    // Format it as you need, e.g. "DD/MM/YYYY"
    return date.toLocaleDateString('en-GB');
  }

  getAverageGrade(studentId: number): number {

    return this.prosecneOcene[studentId] || 0;
  }


  protected readonly formatDateFromString = formatDateFromString;
  protected readonly parseAndFormatDate = parseAndFormatDate;
}
