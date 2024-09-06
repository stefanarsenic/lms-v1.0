import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FakultetService} from "../../../services/fakultet.service";
import {NgForOf, NgIf} from "@angular/common";
import {StudijskiProgramService} from "../../../services/studijski-program.service";
import {TabMenuModule} from "primeng/tabmenu";
import {PredmetService} from "../../../services/predmet.service";
import {Predmet} from "../../../model/predmet";
import {TableModule} from "primeng/table";

@Component({
  selector: 'app-fakultet',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    TabMenuModule,
    TableModule
  ],
  templateUrl: './fakultet.component.html',
  styleUrl: './fakultet.component.css'
})
export class FakultetComponent implements OnInit{

  constructor(
    private route: ActivatedRoute,
    private fakultetService: FakultetService,
    private predmetService: PredmetService
  ) {}

  fakultet: any = {};
  studijskiProgram: any = {};
  rukovodilac: any = {};
  predmeti: Predmet[] = [];

  ngOnInit(): void {
    this.predmeti = [];
    this.studijskiProgram = {};
    this.route.paramMap.subscribe(params => {
      const id: number = Number(this.route.snapshot.paramMap.get('id'));
      this.fakultetService.getById(id).subscribe(data => {
        this.fakultet = data;
        this.studijskiProgram = {};
      });
    });
  }

  getStudijskiProgram(studijski_program_id: number){
    this.studijskiProgram = this.fakultet.studijskiProgrami.find((studijskiProgram:any) =>
      studijskiProgram.id === studijski_program_id);

    this.rukovodilac = this.studijskiProgram.rukovodilac;
    if(this.studijskiProgram) {
      this.predmetService.getPredmetiByStudijskiProgram(this.studijskiProgram.id).subscribe(data => this.predmeti = data);
    }
  }
}
