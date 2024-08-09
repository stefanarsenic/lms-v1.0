import {Component, Injector, ViewChild} from '@angular/core';
import {Button} from "primeng/button";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {FileUploadModule} from "primeng/fileupload";
import {InputTextModule} from "primeng/inputtext";
import {NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {Table, TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {Obavestenje} from "../../../model/obavestenje";
import {UlogaService} from "../../../services/uloga.service";
import {KorisnikService} from "../../../services/korisnik.service";
import {ObavestenjaService} from "../../../services/obavestenja.service";
import {Uloga} from "../../../model/uloga";
import {EditorModule} from "primeng/editor";

@Component({
  selector: 'app-obavestenja',
  standalone: true,
  imports: [
    Button,
    ConfirmDialogModule,
    DialogModule,
    FileUploadModule,
    InputTextModule,
    NgIf,
    PaginatorModule,
    PrimeTemplate,
    TableModule,
    ToastModule,
    ToolbarModule,
    EditorModule
  ],
  templateUrl: './obavestenja.component.html',
  styleUrl: './obavestenja.component.css'
})
export class ObavestenjaComponent extends AppGenerickoComponent<Obavestenje>{

  loading: boolean = true;
  @ViewChild('dt') dt: Table | undefined;

  submitted: boolean = false;

  selektovanaObavestenja:Obavestenje[]=[]
  obavestenjeDialog: boolean = false;
  obavestenja!:Obavestenje[]
  obavestenje:Obavestenje={id:null,naslov:"",sadrzaj:"",vremePostavljanja:new Date(),prilozi:[],realizacijaPredmeta:null};
  obavestenjeZaEdit:any=undefined;

  constructor(private injector: Injector,private messageService: MessageService, private confirmationService: ConfirmationService,
              private obvaesenjeService:ObavestenjaService
  ) {
    super();
    const service = this.injector.get(ObavestenjaService);
    this.initialize(service);
    this.loading=false

  }


  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew() {
    this.selektovanaObavestenja=[]
    this.obavestenje={id:null,naslov:"",sadrzaj:"",vremePostavljanja:new Date(),prilozi:[],realizacijaPredmeta:null};
    this.submitted = false;
    this.obavestenjeDialog = true;
  }
  editProduct(obavestenje: Obavestenje) {
    this.selektovanaObavestenja=[]
    this.obavestenje = { ...obavestenje};
    this.obavestenjeDialog = true;
    this.obavestenjeZaEdit= {...this.obavestenje}
  }
  //TODO:Ne radi grupno brisanje
  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected users?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.items = this.items.filter((val) => !this.selektovanaObavestenja?.includes(val));
        if (this.selektovanaObavestenja) {
          const userIds: (number | null)[] = this.selektovanaObavestenja.map(user => user.id);
          this.service2.deleteRoles(userIds).subscribe((r:any)=>{
            console.log(r)
          })
        }
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000 });
      }
    });
  }

  hideDialog() {
    this.obavestenjeDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    this.submitted = true;
    if (this.obavestenjeZaEdit === undefined) {

      this.service2.create(this.obavestenje).subscribe((r:Uloga)=>{
        this.update(this.obvaesenjeService)
        console.log(r)
      })

    } else {
      this.service2.update(this.obavestenje.id,this.obavestenje).subscribe((r:Uloga)=>{
        this.update(this.obvaesenjeService)
        console.log(r)
      })
      this.obavestenjeZaEdit = undefined

    }
    this.obavestenjeDialog = false;
    this.obavestenje={id:null,naslov:"",sadrzaj:"",vremePostavljanja:new Date(),prilozi:[],realizacijaPredmeta:null};
  }
}
