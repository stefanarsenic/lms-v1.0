import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass, NgFor} from "@angular/common";

@Component({
  selector: 'app-genericka-tabela',
  standalone: true,
  imports: [NgFor, NgClass],
  templateUrl: './genericka-tabela.component.html',
  styleUrl: './genericka-tabela.component.css'
})
export class GenerickaTabelaComponent {

  @Input()
  podaci:any=[]

  @Input()
  kljucevi:any=[]

  @Output()
  brisanjeEvenet:EventEmitter<number>=new EventEmitter<number>();

  @Output()
  izmenaEvenet:EventEmitter<any>=new EventEmitter<any>();

  ukloni(id:any){
    this.brisanjeEvenet.emit(id);
  }

  izmena(id:any){
    this.izmenaEvenet.emit(id);
  }

}
