import {Component, OnInit, ViewChild} from '@angular/core';
import {Table, TableModule} from "primeng/table";
import {InputIconModule} from "primeng/inputicon";
import {IconFieldModule} from "primeng/iconfield";
import {ChipsModule} from "primeng/chips";
import {Predmet} from "../../../model/predmet";
import {PredmetService} from "../../../services/predmet.service";
import {lastValueFrom} from "rxjs";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {FileUploadModule} from "primeng/fileupload";
import {Ripple} from "primeng/ripple";
import {DialogModule} from "primeng/dialog";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ConfirmationService, MessageService} from "primeng/api";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {CheckboxModule} from "primeng/checkbox";
import {DropdownModule} from "primeng/dropdown";
import {SelectButtonModule} from "primeng/selectbutton";
import {Nastavnik} from "../../../model/nastavnik";
import {NastavnikService} from "../../../services/nastavnik.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-predmeti',
  standalone: true,
  imports: [
    TableModule,
    InputIconModule,
    IconFieldModule,
    ChipsModule,
    ToastModule,
    ToolbarModule,
    FileUploadModule,
    Ripple,
    DialogModule,
    ConfirmDialogModule,
    FormsModule,
    NgIf,
    CheckboxModule,
    DropdownModule,
    SelectButtonModule
  ],
  templateUrl: './predmeti.component.html',
  styleUrl: './predmeti.component.css'
})
export class PredmetiComponent implements OnInit{

  @ViewChild('dt') dt: Table | undefined;

  predmeti: Predmet[] = [];
  selectedPredmeti: Predmet[] = [];
  predmet: Predmet = {
    id: 0,
    naziv: '',
    espb: 0,
    obavezan: false,
    brojPredavanja: 0,
    brojVezbi: 0,
    drugiObliciNastave: 0,
    istrazivackiRad: 0,
    ostaliCasovi: 0,
    nastavnik: {} as Nastavnik,
    asistent: {} as Nastavnik
  };

  nastavnici: Nastavnik[] = [];

  loading: boolean = false;
  submitted: boolean = false;
  predmetDialog: boolean = false;

  constructor(
    private predmetService: PredmetService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private nastavnikService: NastavnikService,
    ) {
  }

  ngOnInit(): void {
    this.nastavnikService.getAll().subscribe(data => this.nastavnici = data);
    this.loading = true;
    lastValueFrom(this.predmetService.getAll())
      .then((data: Predmet[]) => {
        this.predmeti = data;
        this.loading = false;
      })
      .catch((error) => {
        console.error(error.message);
      });
  }

  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  openNew() {
    this.predmetDialog = true;
  }

  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.predmeti = this.predmeti.filter(val => !this.selectedPredmeti.includes(val));
        this.selectedPredmeti = [];
        this.messageService.add({severity:'success', summary: 'Successful', detail: 'Products Deleted', life: 3000});
      }
    });
  }

  editProduct(predmet: Predmet) {
    this.predmet = predmet;
    this.predmetDialog = true;
  }

  deleteProduct(predmet: Predmet) {

  }

  otkazi() {
    this.predmetDialog = false;
    this.submitted = false;
  }

  sacuvajPredmet() {
    this.submitted = true;
    if(this.predmet.nastavnik.id && this.predmet.asistent.id) {
      const params: HttpParams = new HttpParams()
        .set("nastavnikId", this.predmet.nastavnik.id)
        .set("asistentId", this.predmet.asistent.id)
      this.predmetService.createPredmet(this.predmet, params).subscribe();
    }
  }
}
