import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {Table, TableModule} from "primeng/table";
import {StudentNaGodiniService} from "../../../../services/student-na-godini.service";
import {Button} from "primeng/button";
import {ChipsModule} from "primeng/chips";
import {StudentNaGodini} from "../../../../model/studentNaGodini";
import {Student} from "../../../../model/student";
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-tabela-studenata',
  standalone: true,
  imports: [
    TableModule,
    Button,
    ChipsModule,
    DropdownModule,
    FormsModule
  ],
  templateUrl: './tabela-studenata.component.html',
  styleUrl: './tabela-studenata.component.css'
})
export class TabelaStudenataComponent implements OnInit{

  @Output() eventEmitter: EventEmitter<any> = new EventEmitter<any>();
  studenti: StudentNaGodini[] = [];
  selectedStudent!: StudentNaGodini;

  constructor(
    private studentNaGodiniService: StudentNaGodiniService,
  ) {}
  ngOnInit(): void {
    this.studentNaGodiniService.getAll().subscribe(data => {
        this.studenti = data;
        console.log(this.studenti);
    });
  }
  selectStudent(){
    if(this.selectedStudent) {
      this.eventEmitter.emit(this.selectedStudent);
    }
  }
}
