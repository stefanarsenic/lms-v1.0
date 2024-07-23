import {Component, Injector, ViewChild} from '@angular/core';
import {GenerickaTabelaComponent} from "../../../../genericko/genericka-tabela/genericka-tabela.component";
import {KorisnikService} from "../../../services/korisnik.service";
import { BrowserModule } from '@angular/platform-browser';
import { ButtonModule } from 'primeng/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {Table, TableModule} from 'primeng/table';
import {ProgressBarModule} from "primeng/progressbar";
import {TagModule} from "primeng/tag";
import {NgClass, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {MultiSelectModule} from "primeng/multiselect";
import {RegistrovaniKorisnik} from "../../../model/registrovaniKorisnik";
import {SliderModule} from "primeng/slider";
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {IconFieldModule} from "primeng/iconfield";
import {InputIconModule} from "primeng/inputicon";
import {InputTextModule} from "primeng/inputtext";
import {ConfirmationService, MessageService, SortEvent} from "primeng/api";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {FileUploadModule} from "primeng/fileupload";
import {DialogModule} from "primeng/dialog";
import {PasswordModule} from "primeng/password";
import {DividerModule} from "primeng/divider";
import {UlogaService} from "../../../services/uloga.service";
import {Uloga} from "../../../model/uloga";
import {Observable} from "rxjs";
import {PravoPristupa} from "../../../model/pravoPristupa";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    GenerickaTabelaComponent,
    ButtonModule,
    TableModule,
    ProgressBarModule,
    TagModule,
    NgClass,
    PaginatorModule,
    MultiSelectModule,
    SliderModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    ConfirmDialogModule,
    ToastModule,
    ToolbarModule,
    FileUploadModule,
    DialogModule,
    PasswordModule,
    DividerModule,
    NgIf
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent extends AppGenerickoComponent<RegistrovaniKorisnik>{

  loading: boolean = true;
  @ViewChild('dt') dt: Table | undefined;

  selectedUsers!: RegistrovaniKorisnik[] | null;

  selektovaneUloge!:Uloga[]
  uloge!:Uloga[]
  korisnik!:RegistrovaniKorisnik
  submitted: boolean = false;
  korisnikDialog: boolean = false;
  pravoPristupa:PravoPristupa={id:undefined,uloga:undefined,registrovaniKorisnik:null}
  korisnikZaEditovanje:any=undefined;
  constructor(private injector: Injector,private messageService: MessageService, private confirmationService: ConfirmationService,
              private ulogeService:UlogaService
  ) {
    super();
    const service = this.injector.get(KorisnikService);
    this.initialize(service);
    this.loading=false
    this.ulogeService.getAll().subscribe(r=>{
      this.uloge=r;
    })
  }

  ngOnInit() {

  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew() {
    this.korisnik = {id:null,ime:"",prezime:"",email:"",korisnickoIme:"",lozinka:"",pravoPristupaSet:[]};
    this.submitted = false;
    this.korisnikDialog = true;
  }
  editProduct(korisnik1: RegistrovaniKorisnik) {
    this.korisnik = { ...korisnik1};
    this.korisnikDialog = true;
    this.korisnikZaEditovanje=this.korisnik;
  }
  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.items = this.items.filter((val) => !this.selectedUsers?.includes(val));
        this.selectedUsers = null;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
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
      for (let uloga of this.selektovaneUloge) {
        this.pravoPristupa.uloga=uloga;
        this.pravoPristupa.registrovaniKorisnik=null
        this.korisnik.pravoPristupaSet.push(this.pravoPristupa)
      }
      console.log(this.korisnik)
      this.service2.create(this.korisnik).subscribe((r:RegistrovaniKorisnik)=>{
        console.log(r)
      })
      // (this.service2.create(this.korisnik) as Observable<any>).subscribe(() => {
      //   this.update(this.service2);
      //   this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
      // });
    } else {
      // (this.service2.update(this.korisnikZaEditovanje.id, this.korisnikZaEditovanje) as Observable<any>).subscribe(() => {
      //   this.update(this.service2);
      //   this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
      // });
    }

    // if (this.korisnik.ime?.trim()) {
    //   if (this.korisnik.id) {
    //     this.korisnik= this.korisnik;
    //     this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
    //   } else {
    //     this.korisnik.id = null
    //     //this.korisnik.pravoPristupaSet=this.selektovaneUloge
    //     this.service2.register(this.korisnik).subscribe((r:RegistrovaniKorisnik)=>{
    //         console.log(r)
    //     })
    //     this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
    //   }

      //this.korisnik = [...this.korisnik];
    this.korisnikDialog = false;
    this.korisnik = {id:0,ime:"",prezime:"",email:"",korisnickoIme:"",lozinka:"",pravoPristupaSet:[]};
    }


}
