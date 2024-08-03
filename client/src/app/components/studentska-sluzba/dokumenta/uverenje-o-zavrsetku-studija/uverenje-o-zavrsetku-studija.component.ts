import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {StudentNaGodini} from "../../../../model/studentNaGodini";
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";
import {PaginatorModule} from "primeng/paginator";
import {TabelaStudenataComponent} from "../tabela-studenata/tabela-studenata.component";
import {PolozeniPredmetService} from "../../../../services/polozeni-predmet.service";
import { parseAndFormatDate } from '../../../../utils/datum-utils';

@Component({
  selector: 'app-uverenje-o-zavrsetku-studija',
  standalone: true,
  imports: [
    PaginatorModule,
    ReactiveFormsModule,
    TabelaStudenataComponent
  ],
  templateUrl: './uverenje-o-zavrsetku-studija.component.html',
  styleUrl: './uverenje-o-zavrsetku-studija.component.css'
})
export class UverenjeOZavrsetkuStudijaComponent implements OnInit{
  form!: FormGroup;
  selektovaniStudent!: StudentNaGodini;
  prosecnaOcenaStudenta: number = 0;
  datumZavrsetkaStudija: any = undefined;
  constructor(private polozeniPredmetService: PolozeniPredmetService) {}

  ngOnInit() {
    this.form = new FormGroup({
      punoIme: new FormControl('', Validators.required),
      brojIndeksa: new FormControl('', Validators.required),
      datumRodjenja: new FormControl('', Validators.required),
      godinaStudija: new FormControl(0, [Validators.required, Validators.min(1)]),
      studijskiProgram: new FormControl('', Validators.required),
      skolskaGodina: new FormControl('', Validators.required)});
  }
  getProsecnaOcenaStudenta(studentId: number){
    this.polozeniPredmetService.getProsecnaOcenaByStudentId(studentId).subscribe(data => this.prosecnaOcenaStudenta = data);
  }
  getStudent(studentNaGodini: StudentNaGodini){
    this.selektovaniStudent = studentNaGodini;
    if (this.selektovaniStudent) {
      if (this.selektovaniStudent.student.datumRodjenja){

        this.form.setValue({
          punoIme: `${this.selektovaniStudent.student.ime} ${this.selektovaniStudent.student.prezime}`,
          brojIndeksa: this.selektovaniStudent.brojIndeksa || '',
          datumRodjenja: parseAndFormatDate(this.selektovaniStudent.student.datumRodjenja.toString()) || '',
          godinaStudija: this.selektovaniStudent.godinaStudija || 0,
          studijskiProgram: this.selektovaniStudent.studijskiProgram.naziv || '',
          skolskaGodina: new Date().getFullYear()});

        if(this.selektovaniStudent.id) {
          this.getProsecnaOcenaStudenta(this.selektovaniStudent.id);
        }
        if (this.selektovaniStudent.datumZavrsetka) {
          this.datumZavrsetkaStudija = parseAndFormatDate(this.selektovaniStudent.datumZavrsetka.toString());
        }

      }

    }
  }
  generatePDF(){
    const elementToPrint: any = document.getElementById('a4');
    html2canvas(elementToPrint, {scale: 1}).then((canvas) => {
      const pdf = new jsPDF();
      pdf.addImage({height: 0, imageData: canvas.toDataURL('image/png'), width: 0, x: 0, y: 0});
      pdf.save('dokument.pdf');
    });
  }
  download() {
    this.generatePDF();
  }
}
