import {Component, Injector, ViewChild} from '@angular/core';
import {Predmet} from "../../../model/predmet";
import {Table, TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {Ishod} from "../../../model/ishod";
import {RealizacijaPredmeta} from "../../../model/realizacijaPredmeta";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {PredmetService} from "../../../services/predmet.service";
import {IshodService} from "../../../services/ishod.service";
import {Obavestenje} from "../../../model/obavestenje";
import {Uloga} from "../../../model/uloga";
import {ObavestenjaService} from "../../../services/obavestenja.service";
import {Button} from "primeng/button";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {FloatLabelModule} from "primeng/floatlabel";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {NgForOf, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {Ripple} from "primeng/ripple";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {EditorModule} from "primeng/editor";
import {RealizacijaPredmetaService} from "../../../services/realizacija-predmeta.service";
import {parseAndFormatDate} from "../../../utils/datum-utils";
import {ProgressSpinnerModule} from "primeng/progressspinner";

@Component({
  selector: 'app-upravljanje-obavestenjima',
  standalone: true,
  imports: [
    Button,
    ConfirmDialogModule,
    DialogModule,
    FloatLabelModule,
    InputTextModule,
    InputTextareaModule,
    NgIf,
    PaginatorModule,
    PrimeTemplate,
    Ripple,
    TableModule,
    ToastModule,
    ToolbarModule,
    EditorModule,
    NgForOf,
    ProgressSpinnerModule
  ],
  templateUrl: './upravljanje-obavestenjima.component.html',
  styleUrl: './upravljanje-obavestenjima.component.css'
})
export class UpravljanjeObavestenjimaComponent extends AppGenerickoComponent<RealizacijaPredmeta>{

  loading: boolean = true;
  obavestenjeDialog: boolean = false;
  predmeti!:Predmet[]
  nastavnikUsername:any
  submitted: boolean = false;
  @ViewChild('dt') dt: Table | undefined;
  @ViewChild('dt2') dt2: Table | undefined;

  reaalcijePredmeta!:RealizacijaPredmeta[]
  combinedList!:any[]
  realizacijaTemp!:RealizacijaPredmeta | null
  selektovanaObavestenja:Obavestenje[]=[]
  obavestenja!:Obavestenje[]
  obavestenje:Obavestenje={id:null,naslov:"",sadrzaj:"",vremePostavljanja:new Date(),prilozi:[],realizacijaPredmeta:null}
  obavestenjeZaEdit:any=undefined;

  constructor(private injector: Injector,private messageService: MessageService, private predmetService:PredmetService,
              private realiazijaPredmeta:RealizacijaPredmetaService,private confirmationService:ConfirmationService) {
    super();
    const service = this.injector.get(ObavestenjaService);
    this.initialize(service);
    this.init()

  }

  init() {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    this.realiazijaPredmeta.getAll().subscribe(r => {
      this.reaalcijePredmeta = r;
    });

    console.log(this.nastavnikUsername);
    if (this.nastavnikUsername) {
      this.predmetService.getPredmetByNastavnik(this.nastavnikUsername).subscribe({
        next: (data) => {
          this.predmeti = data;
          this.loading = false;
          this.combinedList = data.map(predmet => {
            const realizacijaPredmeta = this.reaalcijePredmeta.find(rp => rp.predmet.id === predmet.id);
            return {
              ...predmet,
              realizacijaPredmeta: realizacijaPredmeta || {} // Ensure it doesn't break if null
            };
          });
          console.log(this.combinedList);
        },
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Unable to fetch predmeti' });
          this.loading = false;
        }
      });
    } else {
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Invalid user token' });
      this.loading = false;
    }
  }


  expandedRows = {};


  expandAll() {
    this.expandedRows = this.predmeti.reduce((acc, p) => {
      acc[p.id] = true;
      return acc;
    }, {} as { [key: number]: boolean });
  }

  collapseAll() {
    this.expandedRows = {};
  }

  onRowExpand(event: TableRowExpandEvent) {
    this.obavestenje.realizacijaPredmeta=event.data.realizacijaPredmeta;
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    this.obavestenje.realizacijaPredmeta=null;
  }

  protected readonly Object = Object;

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt2!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }



  openNew(realizacijaPredmeta1:RealizacijaPredmeta) {
    this.selektovanaObavestenja=[]
    this.obavestenje={id:null,naslov:"",sadrzaj:"",vremePostavljanja:new Date(),prilozi:[],realizacijaPredmeta:realizacijaPredmeta1}
    this.submitted = false;
    this.obavestenjeDialog = true;
  }
  editProduct(obavestenje:Obavestenje) {
    this.selektovanaObavestenja=[]
    this.realizacijaTemp=this.obavestenje.realizacijaPredmeta;
    this.obavestenje = { ...obavestenje};
    this.obavestenje.realizacijaPredmeta=this.realizacijaTemp;
    this.obavestenjeDialog = true;
    this.obavestenjeZaEdit= {...this.obavestenje}
  }
  //TODO:Ne radi grupno brisanje
  deleteSelectedProducts() {
    // this.confirmationService.confirm({
    //   message: 'Are you sure you want to delete the selected users?',
    //   header: 'Confirm',
    //   icon: 'pi pi-exclamation-triangle',
    //   accept: () => {
    //     this.items = this.items.filter((val) => !this.selektovanaObavestenja?.includes(val));
    //     if (this.selektovanaObavestenja) {
    //       const userIds: (number | null)[] = this.selektovanaObavestenja.map(user => user.id);
    //       this.service2.deleteRoles(userIds).subscribe((r:any)=>{
    //         console.log(r)
    //       })
    //     }
    //     this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000 });
    //   }
    // });
  }

  hideDialog() {
    this.obavestenjeDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    this.submitted = true;
    if (this.obavestenjeZaEdit === undefined) {
      this.service2.create(this.obavestenje).subscribe((r:Obavestenje)=>{
        this.init()
        this.messageService.add({ severity: 'success', summary: 'Obavestenje dodato!', life: 3000 });

      })

    } else {
      console.log(this.obavestenje)
      this.service2.update(this.obavestenje.id,this.obavestenje).subscribe((r:Obavestenje)=>{
        this.init()
        this.messageService.add({ severity: 'success', summary: 'Obavestenje azurirano!', life: 3000 });

      })
      this.obavestenjeZaEdit = undefined

    }
    this.obavestenjeDialog = false;
    this.obavestenje={id:null,naslov:"",sadrzaj:"",vremePostavljanja:new Date(),prilozi:[],realizacijaPredmeta:null}
  }

  protected readonly parseAndFormatDate = parseAndFormatDate;
}
