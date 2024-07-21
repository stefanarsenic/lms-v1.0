import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FakultetService} from "../../../services/fakultet.service";
import {NgForOf} from "@angular/common";
import {StudijskiProgramService} from "../../../services/studijski-program.service";

@Component({
  selector: 'app-fakultet',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './fakultet.component.html',
  styleUrl: './fakultet.component.css'
})
export class FakultetComponent implements OnInit{

  constructor(
    private route: ActivatedRoute,
    private fakultetService: FakultetService,
  ) {}

  fakultet: any = {};
  studijskiProgram: any = {};

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = Number(this.route.snapshot.paramMap.get('id'));
      this.fakultetService.getById(id).subscribe(data => {
        this.fakultet = data;
        console.log(this.fakultet);
      });
    });
  }

  getStudijskiProgram(studijski_program_id: number){
    this.studijskiProgram = this.fakultet.studijskiProgrami.find((studijskiProgram:any) => studijskiProgram.id === studijski_program_id);
  }
}
