import {Component, Injector, ViewChild} from '@angular/core';
import {AppGenerickoComponent} from "../../../../genericko/app-genericko/app-genericko.component";
import {Uloga} from "../../../model/uloga";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {UlogaService} from "../../../services/uloga.service";
import {Table, TableModule} from "primeng/table";
import {Button} from "primeng/button";
import {ChipModule} from "primeng/chip";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DialogModule} from "primeng/dialog";
import {DividerModule} from "primeng/divider";
import {FileUploadModule} from "primeng/fileupload";
import {InputTextModule} from "primeng/inputtext";
import {MultiSelectModule} from "primeng/multiselect";
import {NgForOf, NgIf} from "@angular/common";
import {PasswordModule} from "primeng/password";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";

@Component({
  selector: 'app-uloge',
  standalone: true,
  imports: [
    Button,
    ChipModule,
    ConfirmDialogModule,
    DialogModule,
    DividerModule,
    FileUploadModule,
    InputTextModule,
    MultiSelectModule,
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
  templateUrl: './uloge.component.html',
  styleUrl: './uloge.component.css'
})
export class UlogeComponent extends AppGenerickoComponent<Uloga>{

  loading: boolean = true;
  @ViewChild('dt') dt: Table | undefined;
  submitted: boolean = false;

  selektovaneUloge:Uloga[]=[]
  ulogeDialog: boolean = false;
  uloge!:Uloga[]
  uloga:Uloga={id:null,naziv:""}
  ulogeZaEdit:any=undefined;

  constructor(private injector: Injector,private messageService: MessageService, private confirmationService: ConfirmationService,
              private ulogeService:UlogaService
  ) {
    super();
    const service = this.injector.get(UlogaService);
    this.initialize(service);
    this.loading=false
    this.ulogeService.getAll().subscribe(r=>{
      this.uloge=r;
    })
  }
  //TODO:Pretraga po nazivu ne radi
  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew() {
    this.selektovaneUloge=[]
    this.uloga={id:null,naziv:""};
    this.submitted = false;
    this.ulogeDialog = true;
  }
  editProduct(uloga: Uloga) {
    this.selektovaneUloge=[]
    this.uloga = { ...uloga};
    this.ulogeDialog = true;
    this.ulogeZaEdit= {...this.uloga}
  }
  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected users?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.items = this.items.filter((val) => !this.selektovaneUloge?.includes(val));
        if (this.selektovaneUloge) {
          const userIds: (number | null)[] = this.selektovaneUloge.map(user => user.id);
          this.service2.deleteRoles(userIds).subscribe((r:any)=>{
            console.log(r)
          })
        }
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000 });
      }
    });
  }

  hideDialog() {
    this.ulogeDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    this.submitted = true;
    if (this.ulogeZaEdit === undefined) {

      this.service2.create(this.uloga).subscribe((r:Uloga)=>{
        this.update(this.ulogeService)
        console.log(r)
      })

    } else {
      this.service2.update(this.uloga.id,this.uloga).subscribe((r:Uloga)=>{
        this.update(this.ulogeService)
        console.log(r)
      })
      this.ulogeZaEdit = undefined

    }
    this.ulogeDialog = false;
    this.uloga = {id:null,naziv:""};
  }

}
