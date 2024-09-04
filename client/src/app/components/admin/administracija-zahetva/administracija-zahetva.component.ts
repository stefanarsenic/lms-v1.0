import {Component, OnInit} from '@angular/core';
import {Button} from "primeng/button";
import {DatePipe} from "@angular/common";
import {DialogModule} from "primeng/dialog";
import {FormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {TableModule} from "primeng/table";
import {TagModule} from "primeng/tag";
import {ZahtevMaterijalaService} from "../../../services/zahtev-materijala.service";
import {KorisnikService} from "../../../services/korisnik.service";
import {DropdownModule} from "primeng/dropdown";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-administracija-zahetva',
  standalone: true,
  imports: [
    Button,
    DatePipe,
    DialogModule,
    FormsModule,
    InputTextModule,
    PrimeTemplate,
    TableModule,
    TagModule,
    DropdownModule,
    ConfirmDialogModule,
    ToastModule
  ],
  templateUrl: './administracija-zahetva.component.html',
  styleUrl: './administracija-zahetva.component.css',
  providers:[MessageService, ConfirmationService]
})
export class AdministracijaZahetvaComponent  implements OnInit {

  requests: any[] = [];  // To hold the requests
  statuses: any[] = [];  // To hold status options for the dropdown
  displayDialog: boolean = false;  // For showing/hiding the dialog
  newZahtev: any = {};
  username:string=""
  constructor(private trebovanjeService: ZahtevMaterijalaService,private messageService: MessageService,private korisnikService:KorisnikService,
              private confirmationService: ConfirmationService ) { }

  ngOnInit(): void {
    this.loadRequests();
    this.statuses = [
      { label: 'Pending', value: 'PENDING' },
      { label: 'Approved', value: 'APPROVED' },
      { label: 'Rejected', value: 'REJECTED' }
    ];
  }

  // Show the dialog to create a new Zahtev
  showDialog(): void {
    this.newZahtev = {};  // Reset the form
    this.displayDialog = true;
  }

  // Hide the dialog
  hideDialog(): void {
    this.displayDialog = false;
  }

  // Add a new Zahtev
  addZahtev(): void {
    this.newZahtev.datumPodnosenja=new Date()
    let token=localStorage.getItem("token")
    if(token){
      this.username=JSON.parse(atob(token.split(".")[1]))["username"]
      this.korisnikService.getUserInfo(this.username).subscribe(r=>{
        this.newZahtev.korisnik=r
        this.trebovanjeService.addZahtev(this.newZahtev).subscribe(r=> {
          this.displayDialog = false;
          this.loadRequests();  // Reload the requests
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Zahtev je uspešno dodat!'});  // Optional toast notification
        }, error => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'Došlo je do greške prilikom dodavanja zahteva!'});  // Optional toast notification
        });
      })
    }

  }

  loadRequests(): void {
    this.trebovanjeService.getAllRequests().subscribe((data: any[]) => {
      this.requests = data;
    });
  }

  updateStatus(request: any): void {
    request.datumIzmena=new Date()
    let token=localStorage.getItem("token")
    if(token){
      this.username=JSON.parse(atob(token.split(".")[1]))["username"]
      this.korisnikService.getUserInfo(this.username).subscribe(r=>{
        request.admin=r
        this.trebovanjeService.updateRequestStatus(request.id, request).subscribe(() => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Status je uspešno ažuriran!' });
        }, error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Došlo je do greške prilikom ažuriranja statusa!' });
        });
      })
    }

  }

  confirmDelete(id: number): void {
    this.confirmationService.confirm({
      message: 'Da li ste sigurni da želite da obrišete ovaj zahtev?',
      accept: () => {
        this.removeRequest(id);  // If user confirms, proceed with the deletion
      }
    });
  }

  removeRequest(id: number): void {
    this.trebovanjeService.delete(id).subscribe(() => {
      this.loadRequests();  // Reload the list of requests
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Zahtev je uspešno obrisan!' });
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Došlo je do greške prilikom brisanja zahteva!' });
    });
  }

  getSeverity(status: string) {
    switch (status) {
      case 'PENDING':
        return 'warning';
      case 'APPROVED':
        return 'success';
      case 'REJECTED':
        return 'danger';
      default:
        return 'info';
    }
  }
}
