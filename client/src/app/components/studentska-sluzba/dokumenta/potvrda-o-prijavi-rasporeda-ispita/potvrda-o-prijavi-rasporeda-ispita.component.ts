import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {TabelaStudenataComponent} from "../tabela-studenata/tabela-studenata.component";
import {StudentNaGodini} from "../../../../model/studentNaGodini";
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";

@Component({
  selector: 'app-potvrda-o-prijavi-rasporeda-ispita',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    TabelaStudenataComponent
  ],
  templateUrl: './potvrda-o-prijavi-rasporeda-ispita.component.html',
  styleUrl: './potvrda-o-prijavi-rasporeda-ispita.component.css'
})
export class PotvrdaOPrijaviRasporedaIspitaComponent implements OnInit{
  form!: FormGroup;
  selektovaniStudent!: StudentNaGodini;
  constructor() {}

  ngOnInit() {
    this.form = new FormGroup({
      punoIme: new FormControl('', Validators.required),
      brojIndeksa: new FormControl('', Validators.required),
      datumRodjenja: new FormControl('', Validators.required),
      godinaStudija: new FormControl(0, [Validators.required, Validators.min(1)]),
      studijskiProgram: new FormControl('', Validators.required),
      skolskaGodina: new FormControl('', Validators.required)});
  }
  getStudent(studentNaGodini: StudentNaGodini){
    this.selektovaniStudent = studentNaGodini;
    if (this.selektovaniStudent) {
      this.form.setValue({
        punoIme: `${this.selektovaniStudent.student.ime} ${this.selektovaniStudent.student.prezime}`,
        brojIndeksa: this.selektovaniStudent.brojIndeksa || '',
        datumRodjenja: this.selektovaniStudent.student.datumRodjenja || '',
        godinaStudija: this.selektovaniStudent.godinaStudija || 0,
        studijskiProgram: this.selektovaniStudent.studijskiProgram.naziv || '',
        skolskaGodina: new Date().getFullYear()});
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
