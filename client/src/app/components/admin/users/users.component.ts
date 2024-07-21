import { Component } from '@angular/core';
import {GenerickaTabelaComponent} from "../../../../genericko/genericka-tabela/genericka-tabela.component";
import {KorisnikService} from "../../../services/korisnik.service";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    GenerickaTabelaComponent
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent {

  constructor(private korisniciSerice:KorisnikService) {

  }

  korisnici:any=[]



}
