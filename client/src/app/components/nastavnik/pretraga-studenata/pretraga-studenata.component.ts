import {Component, Injector, ViewChild} from '@angular/core';
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
import {Student} from "../../../model/student";
import {Drzava} from "../../../model/drzava";
import {Mesto} from "../../../model/mesto";
import {DrzavaService} from "../../../services/drzava.service";
import {StudentService} from "../../../services/student.service";
import {StudijskiProgram} from "../../../model/studijskiProgram";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {StudentNaGodini} from "../../../model/studentNaGodini";

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
export class PretragaStudenataComponent {

  @ViewChild('dt') dt: Table | undefined;
  loading: boolean = true;
  studijskIprogrami:StudentNaGodini[]=[]
  datum!:any
  constructor(private messageService: MessageService, private confirmationService: ConfirmationService,
              studijskiProgram:StudentNaGodiniService) {

    studijskiProgram.getAll().subscribe((r:StudentNaGodini[])=>{
      this.studijskIprogrami=r;
      console.log(r)
    })

  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }





}
