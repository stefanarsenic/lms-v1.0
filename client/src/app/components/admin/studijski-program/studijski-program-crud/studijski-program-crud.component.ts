import {Component, Injector, OnInit, ViewChild} from '@angular/core';
import {Button} from "primeng/button";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {DividerModule} from "primeng/divider";
import {FileUploadModule} from "primeng/fileupload";
import {InputTextModule} from "primeng/inputtext";
import {MultiSelectModule} from "primeng/multiselect";
import {NgIf} from "@angular/common";
import {PasswordModule} from "primeng/password";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Table, TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {StudijskiProgram} from "../../../../model/studijskiProgram";
import {StudijskiProgramService} from "../../../../services/studijski-program.service";
import {FakultetService} from "../../../../services/fakultet.service";
import {NastavnikService} from "../../../../services/nastavnik.service";
import {Fakultet} from "../../../../model/fakultet";
import {Nastavnik} from "../../../../model/nastavnik";
import {ListboxModule} from "primeng/listbox";
import {DropdownModule} from "primeng/dropdown";

@Component({
  selector: 'app-studijski-program-crud',
  standalone: true,
  imports: [
    Button,
    ConfirmDialogModule,
    DialogModule,
    DividerModule,
    FileUploadModule,
    InputTextModule,
    MultiSelectModule,
    NgIf,
    PasswordModule,
    PrimeTemplate,
    ReactiveFormsModule,
    TableModule,
    ToastModule,
    ToolbarModule,
    FormsModule,
    ListboxModule,
    DropdownModule
  ],
  templateUrl: './studijski-program-crud.component.html',
  styleUrl: './studijski-program-crud.component.css'
})
export class StudijskiProgramCrudComponent implements OnInit{

  @ViewChild('dt') dt: Table | undefined;

  studijskiProgram!: StudijskiProgram;
  studijskiProgrami: StudijskiProgram[] = [];
  selectedPrograms!: StudijskiProgram[] | null;
  submitted: boolean = false;
  studijskiProgramDialog: boolean = false;
  studijskiProgramZaEdit: any = undefined;
  fakulteti: Fakultet[] = [];
  nastavnici: Nastavnik[] = [];

  fakultet: Fakultet | undefined = {
    id: 0,
    naziv: "",
    adresa: undefined,
    univerzitet: undefined,
    dekan: undefined,
    studijskiProgrami: undefined
  };
  rukovodilac: Nastavnik | undefined = {
    id: 0,
    korisnickoIme: "",
    lozinka: "",
    email: "",
    ime: "",
    prezime: "",
    pravoPristupaSet: [],
    biografija: "",
    jmbg: "",
    zvanja: undefined
  };

  constructor(
    private injector: Injector,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private fakultetService: FakultetService,
    private nastavnikService: NastavnikService,
    private studijskiProgramService: StudijskiProgramService
  ) {}

  ngOnInit(): void {
    this.getStudijskiProgrami();
    this.nastavnikService.getAll().subscribe(data => {
      this.nastavnici = data.map((nastavnik: any) => {
        return {
          ...nastavnik,
          punoIme: nastavnik.ime + " " + nastavnik.prezime
        };
      });
    });
    this.fakultetService.getAll().subscribe(data => {
      this.fakulteti = data;
    });
  }
  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
  openNew() {
    this.studijskiProgram = {
      id: 0,
      naziv: "",
      opis: "",
      godineTrajanja: null,
      fakultet: this.fakultet,
      rukovodilac: this.rukovodilac,
    };
    this.submitted = false;
    this.studijskiProgramDialog = true;
  }
  editStudijskiProgram(studijskiProgram: StudijskiProgram) {
    this.studijskiProgram = { ...studijskiProgram};
    this.rukovodilac = studijskiProgram.rukovodilac;
    this.fakultet = studijskiProgram.fakultet;
    this.studijskiProgramDialog = true;
    this.studijskiProgramZaEdit = this.studijskiProgram;
  }
  deleteSelectedPrograms() {
    this.confirmationService.confirm({
      message: 'Da li ste sigurni da zelite da obrisete izabrane studijske programe?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.studijskiProgrami = this.studijskiProgrami.filter((val) => !this.selectedPrograms?.includes(val));
        this.selectedPrograms = null;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Studijski programi obrisani!', life: 3000 });
      }
    });
  }
  hideDialog() {
    this.studijskiProgramDialog = false;
    this.studijskiProgramZaEdit = null;
    this.submitted = false;
  }
  dodajStudijskiProgram(studijskiProgram: StudijskiProgram){
    if(!this.studijskiProgramZaEdit) {
      studijskiProgram.fakultet = this.fakultet;
      studijskiProgram.rukovodilac = this.rukovodilac;
      this.studijskiProgramService.create(studijskiProgram).subscribe(data => {
        this.getStudijskiProgrami();
        this.studijskiProgramDialog = false;
      });
    } //TODO: kada se klikne x na dialogu student za izmenu se ne brise pa nastaje problem 
    else{
      this.studijskiProgramService.update(studijskiProgram.id, studijskiProgram).subscribe(data => {
        this.getStudijskiProgrami();
        this.studijskiProgramDialog = false;
        this.studijskiProgramZaEdit = undefined;
      });
    }
  }
  obrisiStudijskiProgram(id: number){
    this.studijskiProgramService.delete(id).subscribe(() => {
      this.getStudijskiProgrami();
    });
  }
  getStudijskiProgrami(){
    this.studijskiProgramService.getAll().subscribe(data => {
      this.studijskiProgrami = data.map((studijskiProgram: any) => {
        return {
          ...studijskiProgram,
          rukovodilacPunoIme: studijskiProgram.rukovodilac.ime + " " + studijskiProgram.rukovodilac.prezime
        };
      });
    });
  }
}
