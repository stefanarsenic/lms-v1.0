import {Component, Injector, OnInit, ViewChild} from '@angular/core';
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
import {formatDateFromString} from "../../../utils/date-converter";
import {parseAndFormatDate} from "../../../utils/datum-utils";
import {KorisnikService} from "../../../services/korisnik.service";

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
export class StudentiComponent extends AppGenerickoComponent<Student> implements OnInit{

  @ViewChild('dt') dt: Table | undefined;
  loading: boolean = true;
  selectedUsers!: Student[] | null;
  student!: Student;
  submitted: boolean = false;
  korisnikDialog: boolean = false;
  korisnikZaEditovanje: any = undefined;
  editPassword: boolean = false;
  selektovanaDrzava: Drzava = { id: null, naziv: "", mesta: [] };
  selektovanoMesto:Mesto={id: null, naziv: '',drzava:{id: null, naziv: "", mesta: []}}
  mesta: Mesto[] = [];
  drzave: Drzava[] = [];
  datum!: any;
  studenti:Student[]=[]
  constructor(
    private injector: Injector,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private drzavaService: DrzavaService,
    private korisnikService:KorisnikService,
    private studentService:StudentService
  )
  {
    super()
    const service = this.injector.get(StudentService);
    this.initialize(service);
    this.loading = false;

  }

  ngOnInit(): void {

    this.drzavaService.getAll().subscribe((r) => {
      this.drzave = r;
    });
    this.studentService.getAll().subscribe((r) => {
      this.studenti=r;
      this.studenti.map(s=>{
        s.datumRodjenja=this.convertDate(s.datumRodjenja)
      })
    })
  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew() {
    this.student = {
      id: 0,
      korisnickoIme: "",
      lozinka: "",
      email: "",
      ime: "",
      prezime: "",
      pravoPristupaSet: [],
      adresa: { id: null, ulica: "", broj: "", mesto: { id: null, naziv: "", drzava: { id: null, naziv: "", mesta: [] } } },
      jmbg: "",
      datumRodjenja: null
    };
    this.submitted = false;
    this.korisnikDialog = true;
    this.editPassword = false;
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

  editProduct(korisnik1: Student) {
    this.datum = korisnik1.datumRodjenja;
    this.student = { ...korisnik1 };

    if (korisnik1.adresa && korisnik1.adresa.mesto && korisnik1.adresa.mesto.drzava) {
      const foundDrzava = this.drzave.find(drzava => drzava.id === korisnik1.adresa.mesto.drzava.id);
      this.selektovanaDrzava = foundDrzava ? foundDrzava : { id: null, naziv: '', mesta: [] };
      this.mesta = this.selektovanaDrzava.mesta;

      const foundMesto = this.mesta.find(mesto => mesto.id === korisnik1.adresa.mesto.id);
      console.log(foundDrzava)
      if (foundMesto) {
        this.selektovanoMesto = foundMesto;
      } else {
        this.selektovanoMesto = { id: null, naziv: '', drzava: { id: null, naziv: '', mesta: [] } };
      }
    }

    this.korisnikDialog = true;
    this.korisnikZaEditovanje = { ...this.student };
    this.editPassword = true;
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
          this.service2.deleteUsers(userIds).subscribe((r: any) => {
            console.log(r);
          });
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
    this.submitted = true;

    if (this.selektovanaDrzava) {
      this.student.adresa.mesto=this.selektovanoMesto
      this.student.adresa.mesto.drzava = this.selektovanaDrzava;
      this.student.adresa.mesto.drzava.mesta = [];
    }

    if (this.korisnikZaEditovanje === undefined) {
      this.service2.dodaj(this.student).subscribe((r: Student) => {
        this.ngOnInit()
        this.selektovanoMesto = { id: null, naziv: '', drzava: { id: null, naziv: '', mesta: [] } };
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Student created', life: 3000 });

      });
    } else {
      this.service2.update(this.student.id, this.student).subscribe((r: Student) => {
        this.ngOnInit()
        this.selektovanoMesto = { id: null, naziv: '', drzava: { id: null, naziv: '', mesta: [] } };
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Student updated', life: 3000 });


      });
      this.korisnikZaEditovanje = undefined;
    }

    this.korisnikDialog = false;
    this.resetForm();
  }

  override deleteItem(id: number) {
    this.korisnikService.delete(id).subscribe(r=>{
      this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000 });
      this.ngOnInit()
    })
  }

  resetForm() {
    this.student = {
      id: 0,
      korisnickoIme: "",
      lozinka: "",
      email: "",
      ime: "",
      prezime: "",
      pravoPristupaSet: [],
      adresa: { id: null, ulica: "", broj: "", mesto: { id: null, naziv: "", drzava: { id: null, naziv: "", mesta: [] } } },
      jmbg: "",
      datumRodjenja: null
    };
    this.selektovanaDrzava = { id: null, naziv: "", mesta: [] };
  }

  protected readonly parseAndFormatDate = parseAndFormatDate;
}
