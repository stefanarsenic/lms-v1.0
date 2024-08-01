import {Component, Injector, ViewChild} from '@angular/core';
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {Nastavnik} from "../../../model/nastavnik";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {Button} from "primeng/button";
import {ChipModule} from "primeng/chip";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {DividerModule} from "primeng/divider";
import {FileUploadModule} from "primeng/fileupload";
import {FormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {MultiSelectModule} from "primeng/multiselect";
import {NgForOf, NgIf} from "@angular/common";
import {PasswordModule} from "primeng/password";
import {Table, TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {Zvanje} from "../../../model/zvanje";
import {NastavnikService} from "../../../services/nastavnik.service";
import {FloatLabelModule} from "primeng/floatlabel";
import {InputTextareaModule} from "primeng/inputtextarea";
import {NaucnaOblast} from "../../../model/naucnaOblast";
import {NacunaOblastService} from "../../../services/nacuna-oblast.service";
import {DropdownModule} from "primeng/dropdown";
import {TipZvanjaService} from "../../../services/tip-zvanja.service";
import {TipZvanja} from "../../../model/tipZvanja";
import {CalendarModule} from "primeng/calendar";

@Component({
  selector: 'app-nastavnik',
  standalone: true,
  imports: [
    Button,
    ChipModule,
    ConfirmDialogModule,
    DialogModule,
    DividerModule,
    FileUploadModule,
    FormsModule,
    InputTextModule,
    MultiSelectModule,
    NgForOf,
    NgIf,
    PasswordModule,
    PrimeTemplate,
    TableModule,
    ToastModule,
    ToolbarModule,
    FloatLabelModule,
    InputTextareaModule,
    DropdownModule,
    CalendarModule
  ],
  templateUrl: './nastavnik.component.html',
  styleUrl: './nastavnik.component.css'
})
export class NastavnikComponent extends AppGenerickoComponent<Nastavnik>{

  loading: boolean = true;
  @ViewChild('dt') dt: Table | undefined;


  selectedUsers!: Nastavnik[] | null;



  zvanja!:Zvanje[]

  naucnaOblast:NaucnaOblast={id:null,naziv:""}
  nasucneOblasti:NaucnaOblast[]=[]
  selektovanOblast!:NaucnaOblast

  tipoviZvanja:TipZvanja[]=[]
  selektovaniTipZvanja!:TipZvanja

  nastavnik!:Nastavnik
  submitted: boolean = false;
  korisnikDialog: boolean = false;
  selektovanaZvanja:Zvanje[]=[]
  korisnikZaEditovanje:any=undefined;
  editPassword:boolean=false

  zvanje:Zvanje={id:null,datumIzbora:null,datumPrestanka:null,nastavnik:null,tipZvanja: {id:null,naziv:""},naucnaOblast:null}
  noviZvanja: Zvanje[] | undefined = [];
  constructor(private injector: Injector,private messageService: MessageService, private confirmationService: ConfirmationService,private naucnaOblastService:NacunaOblastService,
  private tipZvanjaService:TipZvanjaService) {
    super();
    const service = this.injector.get(NastavnikService);
    this.initialize(service);
    this.loading=false
    console.log(this.items)
    this.naucnaOblastService.getAll().subscribe(r=>{
      this.nasucneOblasti=r;
    })
    this.tipZvanjaService.getAll().subscribe(r=>{
      this.tipoviZvanja=r;
    })

  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew() {

    this.nastavnik = {id:null,ime:"",prezime:"",email:"",korisnickoIme:"",lozinka:"",pravoPristupaSet:[],biografija:"",zvanja:[],jmbg:""};
    this.submitted = false;
    this.korisnikDialog = true;
    this.editPassword=false
  }

  editProduct(korisnik1: Nastavnik) {
    this.selektovanaZvanja=[]
    this.nastavnik = { ...korisnik1 };
    if (korisnik1.zvanja) {
      this.noviZvanja = korisnik1.zvanja.map((zvanje: any) => ({
        datumIzbora: zvanje.datumIzbora ? new Date(zvanje.datumIzbora[0], zvanje.datumIzbora[1] - 1, zvanje.datumIzbora[2], zvanje.datumIzbora[3], zvanje.datumIzbora[4]) : null,
        datumPrestanka: zvanje.datumPrestanka ? new Date(zvanje.datumPrestanka[0], zvanje.datumPrestanka[1] - 1, zvanje.datumPrestanka[2], zvanje.datumPrestanka[3], zvanje.datumPrestanka[4]) : null,
        naucnaOblast: zvanje.naucnaOblast || null,
        tipZvanja: zvanje.tipZvanja || null,
      }) as Zvanje);
    }
    this.korisnikDialog = true;
    this.korisnikZaEditovanje= {...this.nastavnik}
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
    this.submitted = true;
    if (this.korisnikZaEditovanje === undefined) {
      this.nastavnik.zvanja=this.noviZvanja
      this.service2.create(this.nastavnik).subscribe((r:Nastavnik)=>{
        this.update(this.service2)
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Proffesor Created', life: 3000 });
      })
    } else {
      this.nastavnik.zvanja=this.noviZvanja
      console.log(this.nastavnik)
      this.service2.updateNastavnik(this.nastavnik.id,this.nastavnik).subscribe((r:Nastavnik)=>{
        this.update(this.service2)
      })

    }
    this.korisnikDialog = false;
    this.nastavnik = {id:null,ime:"",prezime:"",email:"",korisnickoIme:"",lozinka:"",pravoPristupaSet:[],biografija:"",zvanja:[],jmbg:""};
    this.noviZvanja=[]
  }

  dodajZvanje() {
    this.zvanje.nastavnik={...this.nastavnik}
    if (this.noviZvanja) {
      this.noviZvanja.push({...this.zvanje});
    }
    this.zvanje={id:null,datumIzbora:null,datumPrestanka:null,nastavnik:null,tipZvanja: {id:null,naziv:""},naucnaOblast:null}
  }
  ukloniZvanje(index: number) {
    if (this.noviZvanja) {
      this.noviZvanja.splice(index, 1);
    }
  }

}
