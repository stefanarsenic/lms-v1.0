import {Component, OnInit} from '@angular/core';
import {CalendarModule} from "primeng/calendar";
import {ChartModule} from "primeng/chart";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {StudentNaGodini} from "../../../model/studentNaGodini";
import {ChartData, ChartOptions} from "chart.js";
import {Predmet} from "../../../model/predmet";
import {Obavestenje} from "../../../model/obavestenje";
import {MessageService} from "primeng/api";
import {PredmetService} from "../../../services/predmet.service";
import {StudentNaGodiniService} from "../../../services/student-na-godini.service";
import {Student} from "../../../model/student";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CalendarModule,
    ChartModule,
    NgForOf,
    DatePipe,
    FormsModule,
    NgIf
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{


  currentDate: Date = new Date();

  chartOptions: ChartOptions<'bar'> = {
    responsive: true,
    scales: {
      x: {},
      y: {
        beginAtZero: true,
        max: 100
      }
    }
  };

  loading: boolean = true;

  predmeti!:Predmet[]
  studentUsername:any


  studentNaGodini!:StudentNaGodini[]
  student!:Student

  totalCredits: number = 180; // Example data, replace with actual logic to calculate total credits
  currentGPA: number = 3.8; // Example data, replace with actual logic to calculate GPA
  nextExamDate: Date = new Date('2024-09-15');
  constructor(private messageService: MessageService, private predmetService:PredmetService,private studentNaGodiniService: StudentNaGodiniService){
    const token: string | null = localStorage.getItem('token');
    if (token) {
      this.studentUsername = JSON.parse(atob(token.split(".")[1])).username;
    }
    if (this.studentUsername) {
      this.studentNaGodiniService.getAllByStudentUsername(this.studentUsername).subscribe({
        next: (data) => {
          // Convert date arrays to Date objects
          this.studentNaGodini = data.map(program => {

            // Convert `datumUpisa` if it is an array
            if (Array.isArray(program.datumUpisa)) {
              program.datumUpisa = this.convertArrayToDate(program.datumUpisa);
            }

            // Convert `datumZavrsetka` if it is an array and not null
            if (program.datumZavrsetka && Array.isArray(program.datumZavrsetka)) {
              program.datumZavrsetka = this.convertArrayToDate(program.datumZavrsetka);
            }

            return program;
          });

          // Set the student information from the first program
          this.student = this.studentNaGodini[0].student;
          this.loading = false;
        },
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Unable to fetch predmeti' });
          this.loading = false;
        }
      });
    } else {
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Invalid user token' });
      this.loading = false;
    }


  }

  viewCourse(predmet: Predmet): void {
    console.log('View course:', predmet.naziv);
    // Implement course viewing logic here
  }
  convertArrayToDate(dateArray: number[]): Date {
    // The array is expected to be in the format [year, month, day, hour, minute, second, millisecond]
    return new Date(
      dateArray[0], // year
      dateArray[1] - 1, // month (0-based in JavaScript)
      dateArray[2], // day
      dateArray[3] || 0, // hour
      dateArray[4] || 0, // minute
      dateArray[5] || 0, // second
      dateArray[6] || 0 // millisecond
    );
  }

  ngOnInit(): void {

  }

}
