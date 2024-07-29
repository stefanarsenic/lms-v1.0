import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {Table, TableModule} from "primeng/table";
import {StudentNaGodiniService} from "../../../../services/student-na-godini.service";
import {Button} from "primeng/button";
import {ChipsModule} from "primeng/chips";
import {StudentNaGodini} from "../../../../model/studentNaGodini";

@Component({
  selector: 'app-tabela-studenata',
  standalone: true,
  imports: [
    TableModule,
    Button,
    ChipsModule
  ],
  templateUrl: './tabela-studenata.component.html',
  styleUrl: './tabela-studenata.component.css'
})
export class TabelaStudenataComponent implements OnInit{

  @ViewChild('dt1') dt: Table | undefined;
  @Output() eventEmitter: EventEmitter<any> = new EventEmitter<any>();
  studenti: any[] = [];

  constructor(
    private studentNaGodiniService: StudentNaGodiniService,
  ) {}
  ngOnInit(): void {
    this.studentNaGodiniService.getAll().subscribe(data => {
        this.studenti = data;
    });
  }
  selectStudent(student: StudentNaGodini){
    this.eventEmitter.emit(student);
  }
  clear(table: Table) {
    table.clear();
  }
  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
}
