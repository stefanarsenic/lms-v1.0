import {Component, OnInit} from '@angular/core';
import {PaginatorModule} from "primeng/paginator";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {StudentNaGodini} from "../../../../model/studentNaGodini";
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";
import {TabelaStudenataComponent} from "../tabela-studenata/tabela-studenata.component";

@Component({
  selector: 'app-priznanje-ispita',
  standalone: true,
  imports: [
    PaginatorModule,
    ReactiveFormsModule,
    TabelaStudenataComponent
  ],
  templateUrl: './priznanje-ispita.component.html',
  styleUrl: './priznanje-ispita.component.css'
})
export class PriznanjeIspitaComponent implements OnInit{
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