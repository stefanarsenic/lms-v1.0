import {Component, Injector, ViewChild} from '@angular/core';
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {Student} from "../../../model/student";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {NacunaOblastService} from "../../../services/nacuna-oblast.service";
import {TipZvanjaService} from "../../../services/tip-zvanja.service";
import {NastavnikService} from "../../../services/nastavnik.service";
import {StudentService} from "../../../services/student.service";
import {Button, ButtonDirective} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {DividerModule} from "primeng/divider";
import {DropdownModule} from "primeng/dropdown";
import {FileUploadModule} from "primeng/fileupload";
import {FloatLabelModule} from "primeng/floatlabel";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {NgForOf, NgIf} from "@angular/common";
import {PasswordModule} from "primeng/password";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Table, TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {Nastavnik} from "../../../model/nastavnik";
import {Zvanje} from "../../../model/zvanje";
import {NaucnaOblast} from "../../../model/naucnaOblast";
import {TipZvanja} from "../../../model/tipZvanja";
import {Mesto} from "../../../model/mesto";
import {Drzava} from "../../../model/drzava";
import {DrzavaService} from "../../../services/drzava.service";

@Component({
  selector: 'app-studenti',
  standalone: true,
  imports: [
    Button,
    ButtonDirective,
    CalendarModule,
    ConfirmDialogModule,
    DialogModule,
    DividerModule,
    DropdownModule,
    FileUploadModule,
    FloatLabelModule,
    InputTextModule,
    InputTextareaModule,
    NgForOf,
    NgIf,
    PasswordModule,
    PrimeTemplate,
    ReactiveFormsModule,
    TableModule,
    ToastModule,
    ToolbarModule,
    FormsModule
  ],
  templateUrl: './studenti.component.html',
  styleUrl: './studenti.component.css'
})
export class StudentiComponent extends AppGenerickoComponent<Student>{

  @ViewChild('dt') dt: Table | undefined;
  loading: boolean = true;

  selectedUsers!: Student[] | null;


  student!:Student
  submitted: boolean = false;
  korisnikDialog: boolean = false;
  korisnikZaEditovanje:any=undefined;
  editPassword:boolean=false
  selektovanaDrzava:Drzava={id:null,naziv:"",mesta:[]};
  mesta!:Mesto[]
  drzave!:Drzava[]

  datum!:any
  constructor(private injector: Injector,private messageService: MessageService, private confirmationService: ConfirmationService,
              private drzavaService:DrzavaService) {
    super();
    const service = this.injector.get(StudentService);
    this.initialize(service);
    console.log(this.items)
    this.loading=false
    this.drzavaService.getAll().subscribe(r=>{
      this.drzave=r;
    })


  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew() {

    this.student = {id:null,korisnickoIme:"",lozinka:"",email:"",ime:"",prezime:"",pravoPristupaSet:[],adresa:{id:null,ulica:"",broj:"",mesto:{id:null,naziv:"",drzava:{id:null,naziv:"",mesta:[]}}},jmbg:"",datumRodjenja:null};
    this.submitted = false;
    this.korisnikDialog = true;
    this.editPassword=false
    console.log(this.drzave)
  }

  editProduct(korisnik1: Student) {

    this.datum=korisnik1.datumRodjenja;
    this.student = { ...korisnik1 };
    this.student.datumRodjenja=new Date(this.datum[0], this.datum[1] - 1,this.datum[2],this.datum[3], this.datum[4])
    //TODO:Drazva i mesto se lepo ne ucitaju
    this.selektovanaDrzava=korisnik1.adresa.mesto.drzava
    this.korisnikDialog = true;
    this.korisnikZaEditovanje= {...this.student}
    this.editPassword=true

  }

  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected users?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.items = this.items.filter((val) => !this.selectedUsers?.includes(val));
        if (this.selectedUsers) {
          const userIds: (number | null)[] = this.selectedUsers.map(user => user.id);
          this.service2.deleteUsers(userIds).subscribe((r:any)=>{
            console.log(r)
          })
        }
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000 });
      }
    });
  }

  hideDialog() {
    this.korisnikDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    this.submitted = false;
    this.student.adresa.mesto.drzava=this.selektovanaDrzava
    this.student.adresa.mesto.drzava.mesta=[]
    if (this.korisnikZaEditovanje === undefined) {
      this.service2.create(this.student).subscribe((r:Student)=>{
        this.update(this.service2)
        console.log(r)
      })
     console.log(this.student)
    } else {
      this.service2.update(this.student.id,this.student).subscribe((r:Student)=>{
        console.log(r)
      })
      this.korisnikZaEditovanje = undefined

    }
    this.korisnikDialog = true;
    this.student = {id:null,korisnickoIme:"",lozinka:"",email:"",ime:"",prezime:"",pravoPristupaSet:[],adresa:{id:null,ulica:"",broj:"",mesto:{id:null,naziv:"",drzava:{id:null,naziv:"",mesta:[]}}},jmbg:"",datumRodjenja:null};
    this.selektovanaDrzava={id:null,naziv:"",mesta:[]}
  }

}
