import {Component, Injector, ViewChild} from '@angular/core';
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {Nastavnik} from "../../../model/nastavnik";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {UlogaService} from "../../../services/uloga.service";
import {KorisnikService} from "../../../services/korisnik.service";
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
import {RegistrovaniKorisnik} from "../../../model/registrovaniKorisnik";
import {Uloga} from "../../../model/uloga";
import {PravoPristupa} from "../../../model/pravoPristupa";
import {Zvanje} from "../../../model/zvanje";
import {Observable} from "rxjs";
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
  noviZvanja: Zvanje[] = [];
  constructor(private injector: Injector,private messageService: MessageService, private confirmationService: ConfirmationService,private naucnaOblastService:NacunaOblastService,
  private tipZvanjaService:TipZvanjaService) {
    super();
    const service = this.injector.get(NastavnikService);
    this.initialize(service);
    this.loading=false
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
    this.nastavnik = { ...korisnik1};
    this.korisnikDialog = true;
    this.korisnikZaEditovanje= {...this.nastavnik}

    this.editPassword=true

  }
  //todo:Treba uraditi: ADD,EDIT
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
      // for (let uloga of this.selektovaneUloge) {
      //   this.pravoPristupa.uloga=uloga;
      //   this.pravoPristupa.registrovaniKorisnik
      //   this.listaPristupa.push(this.pravoPristupa)
      //   this.pravoPristupa={id:undefined,uloga:undefined,registrovaniKorisnik:null}
      // }
      // this.korisnik.pravoPristupaSet=this.listaPristupa;
      //
      // (this.service2.update(this.korisnik.id, this.korisnik) as Observable<any>).subscribe(() => {
      //   this.update(this.service2)
      //   this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'User Updated', life: 3000 });
      // });
      // this.korisnikZaEditovanje = undefined

    }
    this.korisnikDialog = false;
    this.nastavnik = {id:null,ime:"",prezime:"",email:"",korisnickoIme:"",lozinka:"",pravoPristupaSet:[],biografija:"",zvanja:[],jmbg:""};
  }

  dodajZvanje() {
    this.zvanje.nastavnik={...this.nastavnik}
    this.noviZvanja.push({...this.zvanje});
    this.zvanje={id:null,datumIzbora:null,datumPrestanka:null,nastavnik:null,tipZvanja: {id:null,naziv:""},naucnaOblast:null}
  }
  ukloniZvanje(index: number) {
    this.noviZvanja.splice(index, 1);
  }

}
