import {ChangeDetectorRef, Component, OnInit, signal, ViewChild} from '@angular/core';
import {ButtonDirective} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {FullCalendarComponent, FullCalendarModule} from "@fullcalendar/angular";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ToastModule} from "primeng/toast";
import {CalendarOptions, EventApi, EventClickArg} from "@fullcalendar/core";
import rrulePlugin from "@fullcalendar/rrule";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import listPlugin from "@fullcalendar/list";
import {Predmet} from "../../../model/predmet";
import {Nastavnik} from "../../../model/nastavnik";
import {Fajl} from "../../../model/fajl";
import {PredmetService} from "../../../services/predmet.service";
import {NastavnikService} from "../../../services/nastavnik.service";
import {GoogleDriveService} from "../../../services/google-drive.service";
import {MessageService} from "primeng/api";
import {HttpParams} from "@angular/common/http";
import {lastValueFrom} from "rxjs";
import {formatDateFromString} from "../../../utils/date-converter";
import {EvaluacijaZnanja} from "../../../model/evaluacijaZnanja";
import {EvaluacijaZnanjaService} from "../../../services/evaluacija-znanja.service";

@Component({
  selector: 'app-instrumenti-evaluacije',
  standalone: true,
  imports: [
    ButtonDirective,
    DialogModule,
    DropdownModule,
    FullCalendarModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    ToastModule,
    FormsModule
  ],
  templateUrl: './instrumenti-evaluacije.component.html',
  styleUrl: './instrumenti-evaluacije.component.css'
})
export class InstrumentiEvaluacijeComponent implements OnInit{
  @ViewChild('calendar') calendarComponent!: FullCalendarComponent;

  calendarVisible = signal(true);
  calendarOptions = signal<CalendarOptions>({
    plugins: [
      rrulePlugin,
      interactionPlugin,
      dayGridPlugin,
      timeGridPlugin,
      listPlugin,
    ],
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'timeGridWeek,timeGridDay,listWeek'
    },
    initialView: 'timeGridWeek',
    timeZone: 'local',
    initialEvents: [],
    contentHeight: 600,
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this)
  });
  currentEvents = signal<EventApi[]>([]);

  clickInfo!: EventClickArg;

  predmeti: Predmet[] = [];
  selectedPredmet!: Predmet;

  evaluacijeZnanja: EvaluacijaZnanja[] = [];

  nastavnik!: Nastavnik;

  selectedFile: File | null = null;
  uploadedFile!: Fajl;

  visible: boolean = false;
  loading: boolean = false;

  opis!: string;

  constructor(
    private changeDetector: ChangeDetectorRef,
    private evaluacijaZnanjaService: EvaluacijaZnanjaService,
    private predmetService: PredmetService,
    private nastavnikService: NastavnikService,
    private googleDriveService: GoogleDriveService,
    private messageService: MessageService
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    const token: string | null = localStorage.getItem('token');
    if (token) {
      const nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
      const params: HttpParams = new HttpParams()
        .set("korisnickoIme", nastavnikUsername);

      this.nastavnikService.getByKorisnickoIme(params).subscribe(data => this.nastavnik = data);

      this.predmetService.getPredmetByNastavnik(nastavnikUsername).subscribe(data => {
        this.predmeti = data;
        this.loading = false;
      });
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  async uploadFile(): Promise<any> {
    if (this.selectedFile) {
      return lastValueFrom(this.googleDriveService.uploadFile(this.selectedFile));
    }
    return null;
  }

  async sacuvaj() {
    this.uploadedFile = await this.uploadFile();

    let fajl: Fajl = {
      id: 0,
      sifra: this.uploadedFile.sifra,
      opis: this.opis
    }

    this.evaluacijaZnanjaService.updateInstrumentEvaluacije(Number(this.clickInfo.event.id), fajl).subscribe({
      next: () => {
        this.messageService.add({ severity: "success", summary: "Success", detail: "Evaluacija znanja uspesno azurirana", life: 1000 });
        this.loadEvents();
        this.visible = false;
      },
      error:(err) => {
        console.error(err);
        this.messageService.add({ severity: "error", summary: "Error", detail: err.message, life: 1000 });
      }
    });
  }

  otkazi(){
    this.visible = false;
  }

  loadEvents(){
    this.calendarComponent.getApi().removeAllEvents();
    this.evaluacijeZnanja = [];
    if(this.selectedPredmet && this.nastavnik.id) {
      const params: HttpParams = new HttpParams()
        .set("nastavnikId", this.nastavnik.id)
        .set("predmetId", this.selectedPredmet.id)

      this.evaluacijaZnanjaService.getAllByNastavnikAndPredmet(params).subscribe((data) => {
        this.evaluacijeZnanja = data;
        for(let evaluacijaZnanja of data){
          this.addEventToCalendar(evaluacijaZnanja);
        }
      });
    }
  }

  getById(){
    if(this.clickInfo) {
      let ez = this.evaluacijeZnanja.find(e => e.id === Number(this.clickInfo.event.id));

      if (ez) {
        return ez;
      }
    }

    return undefined;
  }

  addEventToCalendar(evaluacijaZnanja: EvaluacijaZnanja){
    const calendarApi = this.calendarComponent.getApi();

    calendarApi.addEvent({
      id: evaluacijaZnanja.id?.toString(),
      start: formatDateFromString(evaluacijaZnanja.vremePocetka.toString()),
      end: formatDateFromString(evaluacijaZnanja.vremeZavrsetka.toString()),
      title: this.selectedPredmet.naziv,
      description: evaluacijaZnanja.tipEvaluacije?.naziv
    });
  }

  handleEventClick(clickInfo: EventClickArg) {
    this.clickInfo = clickInfo;
    this.visible = true;
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents.set(events);
    this.changeDetector.detectChanges();
  }

}
