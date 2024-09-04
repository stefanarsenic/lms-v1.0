import {Component, OnInit} from '@angular/core';
import {ZahtevMaterijalaService} from "../../../services/zahtev-materijala.service";
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { PaginatorModule } from 'primeng/paginator';
import {DatePipe} from "@angular/common";
import {DialogModule} from "primeng/dialog";
import {MessageService} from "primeng/api";
import {KorisnikService} from "../../../services/korisnik.service";

@Component({
  selector: 'app-zahtev-materijala',
  standalone: true,
  imports: [
    TableModule,
    InputTextModule,
    ButtonModule,
    TagModule,
    PaginatorModule,
    DatePipe,
    DialogModule

  ],
  templateUrl: './zahtev-materijala.component.html',
  styleUrl: './zahtev-materijala.component.css'
})
export class ZahtevMaterijalaComponent implements OnInit {

  requests: any[] = [];  // To hold the requests
  statuses: any[] = [];  // To hold status options for the dropdown
  displayDialog: boolean = false;  // For showing/hiding the dialog
  newZahtev: any = {};
  username:string=""
  constructor(private trebovanjeService: ZahtevMaterijalaService,private messageService: MessageService,private korisnikService:KorisnikService) { }

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
    let token = localStorage.getItem("token")
    if (token) {
      this.username = JSON.parse(atob(token.split(".")[1]))["username"]
      this.korisnikService.getUserInfo(this.username).subscribe(r => {

        this.trebovanjeService.findByUsername(this.username).subscribe((data: any[]) => {
          this.requests = data;
        });

      })
    }
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
