import {Component, OnInit} from '@angular/core';
import {PaginatorModule} from "primeng/paginator";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {StudentNaGodini} from "../../../../model/studentNaGodini";
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";
import {TabelaStudenataComponent} from "../tabela-studenata/tabela-studenata.component";
import {PolozeniPredmetService} from "../../../../services/polozeni-predmet.service";
import {PolozeniPredmet} from "../../../../model/polozeniPredmet";
import {PredmetService} from "../../../../services/predmet.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-uverenje-o-polozenim-ispitima',
  standalone: true,
  imports: [
    PaginatorModule,
    ReactiveFormsModule,
    TabelaStudenataComponent,
    NgForOf
  ],
  templateUrl: './uverenje-o-polozenim-ispitima.component.html',
  styleUrl: './uverenje-o-polozenim-ispitima.component.css'
})
export class UverenjeOPolozenimIspitimaComponent implements OnInit{
  form!: FormGroup;
  selektovaniStudent!: StudentNaGodini;
  polozeniIspitiStudenta: any[] = [];
  constructor(
    private polozeniPredmetiService: PolozeniPredmetService,
  ) {}

  ngOnInit() {
    this.form = new FormGroup({
      punoIme: new FormControl('', Validators.required),
      brojIndeksa: new FormControl('', Validators.required),
      datumRodjenja: new FormControl('', Validators.required),
      godinaStudija: new FormControl(0, [Validators.required, Validators.min(1)]),
      studijskiProgram: new FormControl('', Validators.required),
      skolskaGodina: new FormControl('', Validators.required)});
  }
  getPolozeniIspitiStudenta(studentId: number){
    this.polozeniPredmetiService.getByStudentId(studentId).subscribe((data: PolozeniPredmet[]) => {
      this.polozeniIspitiStudenta = data;
    });
    console.log(this.polozeniIspitiStudenta);
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

      if(this.selektovaniStudent.id) {
        this.getPolozeniIspitiStudenta(this.selektovaniStudent.id);
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
