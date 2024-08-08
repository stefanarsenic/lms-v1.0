import {Component, Injector, ViewChild} from '@angular/core';
import {Button} from "primeng/button";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {Ripple} from "primeng/ripple";
import {Table, TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {Predmet} from "../../../model/predmet";
import {PredmetService} from "../../../services/predmet.service";
import {Uloga} from "../../../model/uloga";
import {Ishod} from "../../../model/ishod";
import {ToolbarModule} from "primeng/toolbar";
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {NastavnikService} from "../../../services/nastavnik.service";
import {InputTextModule} from "primeng/inputtext";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {FloatLabelModule} from "primeng/floatlabel";
import {InputTextareaModule} from "primeng/inputtextarea";
import {IshodService} from "../../../services/ishod.service";

@Component({
  selector: 'app-uredjivanje-silabusa',
  standalone: true,
  imports: [
    Button,
    PrimeTemplate,
    Ripple,
    TableModule,
    ToastModule,
    ToolbarModule,
    InputTextModule,
    ConfirmDialogModule,
    DialogModule,
    FormsModule,
    NgIf,
    FloatLabelModule,
    InputTextareaModule
  ],
  templateUrl: './uredjivanje-silabusa.component.html',
  styleUrl: './uredjivanje-silabusa.component.css'
})
export class UredjivanjeSilabusaComponent extends AppGenerickoComponent<Ishod>{

  loading: boolean = true;
  silabusDialog: boolean = false;
  predmeti!:Predmet[]
  nastavnikUsername:any
  submitted: boolean = false;
  @ViewChild('dt') dt: Table | undefined;
  @ViewChild('dt2') dt2: Table | undefined;

  selektovaniIshodi:Ishod[]=[]
  ishod:Ishod={id:null,opis:"",predmet:null}
  ishodZaEdit:any=undefined;
  ishodi!:Ishod[]

  tempPredmet!:Predmet
  constructor(private injector: Injector,private messageService: MessageService, private predmetService:PredmetService,private confirmationService: ConfirmationService,
  private ishodService:IshodService) {
    super();
    const service = this.injector.get(IshodService);
    this.initialize(service);
    this.loading=false
    this.init()

  }

  init(){
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    console.log(this.nastavnikUsername)
    if (this.nastavnikUsername) {
      this.predmetService.getPredmetByNastavnik(this.nastavnikUsername).subscribe({
        next: (data) => {
          this.predmeti = data;
          this.loading = false;
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
    this.messageService.add({ severity: 'info', summary: 'Product Expanded', detail: event.data.name, life: 3000 });
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    this.messageService.add({ severity: 'success', summary: 'Product Collapsed', detail: event.data.name, life: 3000 });
  }

  protected readonly Object = Object;


  applyFilterGlobal($event: any, stringVal: any) {
    this.dt2!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew(predmet:Predmet) {
    this.tempPredmet={...predmet}
    this.tempPredmet.silabus=[]
    this.ishod={id:null,opis:"",predmet:this.tempPredmet}
    this.submitted = false;
    this.silabusDialog = true;
  }
  editProduct(ishod: Ishod,predmet:Predmet) {
    this.ishod = { ...ishod};
    this.ishod.predmet=predmet
    this.silabusDialog = true;
    this.ishodZaEdit= {...this.ishod}
  }
  deleteSelectedProducts(silabusi:Ishod[]) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected users?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        silabusi = silabusi.filter((val) => !this.selektovaniIshodi?.includes(val));
        if (this.selektovaniIshodi) {
          const userIds:(number | null)[] = this.selektovaniIshodi.map(user => user.id);
          if(userIds){
            this.ishodService.deleteIshod(userIds).subscribe((r:any)=>{
              this.init()
            })
          }

        }
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Ishod Deleted', life: 3000 });
      }
    });
  }

  hideDialog() {
    this.silabusDialog = false;
    this.submitted = false;
  }

deleteIshog(id:number){
    this.ishodService.delete(id).subscribe(r=>{
      this.init()
      this.messageService.add({ severity: 'success', summary: 'Ishod obrisan', life: 3000 });
    })
}

  saveProduct() {
    this.submitted = true;
    if (this.ishodZaEdit === undefined) {

      this.ishodService.create(this.ishod).subscribe(r=>{
        this.init()
        this.messageService.add({ severity: 'success', summary: 'Ishod dodat', life: 3000 });
      })


    } else {
      if(this.ishod.id){
        this.ishodService.update(this.ishod.id,this.ishod).subscribe(r=>{
          this.init()
          this.messageService.add({ severity: 'success', summary: 'Ishod azuriran', life: 3000 });
        })
      }

      this.ishodZaEdit = undefined

    }
    this.silabusDialog = false;
    this.ishod = {id:null,opis:"",predmet:null}
  }
}
