import {ChangeDetectorRef, Component, OnInit, signal, ViewChild, ViewRef} from '@angular/core';
import {DropdownModule} from "primeng/dropdown";
import {FormsModule} from "@angular/forms";
import {IspitniRok} from "../../../../model/ispitniRok";
import {IspitniRokService} from "../../../../services/ispitni-rok.service";
import {FullCalendarComponent, FullCalendarModule} from "@fullcalendar/angular";
import {NgIf} from "@angular/common";
import {CalendarOptions, DateSelectArg, EventApi, EventClickArg, EventChangeArg, EventDropArg} from "@fullcalendar/core";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import listPlugin from "@fullcalendar/list";
import {formatDateFromString} from "../../../../utils/date-converter";
import {StudijskiProgram} from "../../../../model/studijskiProgram";
import {Predmet} from "../../../../model/predmet";
import {PredmetService} from "../../../../services/predmet.service";
import {StudijskiProgramService} from "../../../../services/studijski-program.service";
import {IspitService} from "../../../../services/ispit.service";
import {Button} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {CalendarModule} from "primeng/calendar";
import {Ispit} from "../../../../model/ispit";
import {lastValueFrom} from "rxjs";
import {HttpParams} from "@angular/common/http";
import {ConfirmationService, MessageService} from "primeng/api";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {ToastModule} from "primeng/toast";
import {formatTimeFromString} from "../../../../utils/time-converter";
import {Ripple} from "primeng/ripple";

@Component({
  selector: 'app-raspored-ispita',
  standalone: true,
  imports: [
    DropdownModule,
    FormsModule,
    FullCalendarModule,
    NgIf,
    Button,
    DialogModule,
    CalendarModule,
    ConfirmPopupModule,
    ToastModule,
    Ripple
  ],
  templateUrl: './ispiti.component.html',
  styleUrl: './ispiti.component.css'
})
export class IspitiComponent implements OnInit{

  ispiti: Ispit[] = [];
  predmeti: Predmet[] = [];
  selectedPredmet!: any;
  studijskiProgrami: StudijskiProgram[] = [];
  selectedStudijskiProgram!: StudijskiProgram;
  godineTrajanja: number[] = [];
  godina: number | null = null;
  ispitniRokovi: IspitniRok[] = [];
  selectedIspitniRok!: IspitniRok;
  kreairanjeIspitaVisible: boolean = false;
  izmenaIspitaVisible: boolean = false;
  selectedDateInfo!: DateSelectArg;
  clickInfo!: EventClickArg;
  errorVisible: boolean = false;
  errorMessage: string = "";
  showWarningDialog: boolean = false;
  warningMessage: string = '';
  sacuvajIzmeneBtnDisabled: boolean = true;

  @ViewChild('calendar') calendarComponent!: FullCalendarComponent;

  calendarVisible = signal(true);
  calendarOptions= signal<CalendarOptions>({
    plugins: [
      interactionPlugin,
      dayGridPlugin,
      timeGridPlugin,
      listPlugin,
    ],
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
    initialView: 'dayGridMonth',
    timeZone: 'local',
    contentHeight: 600,
    initialEvents: [],
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    select: this.handleDateSelect.bind(this),
    eventDrop: this.handleEventDrop.bind(this),
    eventChange: this.handleEventChange.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this),

  });
  currentEvents = signal<EventApi[]>([]);

  constructor(
    private ispitniRokService: IspitniRokService,
    private changeDetector: ChangeDetectorRef,
    private predmetService: PredmetService,
    private studijskiProgramService: StudijskiProgramService,
    private ispitService: IspitService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
    ) {}

  ngOnInit(): void {
    this.ispitniRokService.getAll().subscribe(data => this.ispitniRokovi = data);
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
  }

  async loadEvents() {
    const calendarApi = this.calendarComponent.getApi();

    this.ispitService.getAllByIspitniRokAndStudijskiProgram(this.selectedIspitniRok.id, this.selectedStudijskiProgram.id).subscribe(
      data => {
        this.ispiti = data;
        for(let ispit of data){
          calendarApi.addEvent({
            id: ispit.id.toString(),
            title: `${ispit.predmet.naziv} - ${formatTimeFromString(ispit.pocetakIspita.toString())}`,
            start: formatDateFromString(ispit.pocetakIspita.toString()),
            end: formatDateFromString(ispit.krajIspita.toString()),
          });
        }
      }
    )
  }

  resetujKalendar(){
    this.sacuvajIzmeneBtnDisabled = true;
    this.clearEvents();
    this.loadEvents();
  }

  studijskiProgramChange(){
    this.clearEvents();
    this.getPredmeti();
    if(this.selectedIspitniRok){
      this.loadEvents();
    }
  }

  getPredmeti(){
    if(this.selectedStudijskiProgram){
      this.predmetService.getPredmetiNotExistingInIspitiByStudijskiProgram(this.selectedStudijskiProgram.id).subscribe( data => {
        this.predmeti = data;
      });
    }
  }

  selektujIspitniRok(){
    this.clearEvents();
    this.loadEvents();
  }

  async createIspit(ispit: Ispit): Promise<any>{
    const params = new HttpParams()
      .set('predmetId', this.selectedPredmet.id)
      .set('ispitniRokId', this.selectedIspitniRok.id)
      .set('studijskiProgramId', this.selectedStudijskiProgram.id);

    try {
     return await lastValueFrom(this.ispitService.createIspit(ispit, params));
    }
    catch (error: any) {
     throw new Error(error.message || error.toString());
    }
  }
  //TODO: request procesing neka animacija, loading
  dialogSave(){
    const calendarApi = this.calendarComponent.getApi();

    if(!this.selectedPredmet){
      this.setErrorMessage();
      return
    }

    const ispit: Ispit = {
      id: 0,
      pocetakIspita: new Date(this.selectedDateInfo.startStr),
      krajIspita: new Date(this.selectedDateInfo.endStr),
      predmet: this.selectedPredmet,
      ispitniRok: this.selectedIspitniRok,
      studijskiProgram: this.selectedStudijskiProgram
    }

    this.createIspit(ispit)
      .then(
        (createdIspit: Ispit) => {
          this.getPredmeti();
          this.addEventToCalendar(calendarApi, createdIspit.id);
          this.resetForm();
          this.messageService.add({severity:'success', summary:'Confirmed', detail:'Kreiranje uspesno'});
        })
      .catch((error) => {
        console.error(error);
      });
  }

  sacuvajIzmene(event: any){
    const calendarApi = this.calendarComponent.getApi();

    this.confirmationService.confirm({
      acceptLabel: "Da",
      rejectLabel: "Ne",
      target: event.target,
      message: 'Da li ste sigurni da zelite da nastavite?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        const events = calendarApi.getEvents().slice(1);
        for(let event of events){

          const pocetakIspita: Date = new Date(event.startStr)
          const krajIspita: Date = new Date(event.endStr)

          this.ispitService.updatePocetakIKraj(Number(event.id), pocetakIspita, krajIspita).subscribe();
        }
        this.sacuvajIzmeneBtnDisabled = true;
        this.messageService.add({severity:'success', summary:'Confirmed', detail:'Uspesno sacuvano'});
      },
      reject: () => {
        this.messageService.add({severity:'error', summary:'Rejected', detail:'Operacija prekinuta'});
      }
    });
  }

  deleteIspit(event: any){
    this.confirmationService.confirm({
      acceptLabel: "Da",
      rejectLabel: "Ne",
      target: event.target,
      message: 'Da li ste sigurni da zelite da obrisete ovaj ispit?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.ispitService.delete(Number(this.clickInfo.event.id)).subscribe({
          next: () => {
            this.resetForm();
            this.getPredmeti();
            this.clickInfo.event.remove();
            this.messageService.add({severity:'success', summary:'Confirmed', detail:'Brisanje uspesno', life: 1000});
          },
          error: () => {
            console.error(`Greska u brisanju ${this.clickInfo.event}`)
          }
        });
      },
      reject: () => {
        this.messageService.add({severity:'error', summary:'Rejected', detail:'Operacija prekinuta', life: 1000});
      }
    });
  }

  resetForm(){
    this.selectedPredmet = null;
    this.errorMessage = "";
    this.errorVisible = false;
    this.kreairanjeIspitaVisible = false;
    this.izmenaIspitaVisible = false;
  }

  addEventToCalendar(calendarApi: any, eventId: any){
    calendarApi.addEvent({
      id: eventId,
      title: `${this.selectedPredmet.naziv} - ${formatTimeFromString(this.selectedDateInfo.startStr)}`,
      start: this.selectedDateInfo.startStr,
      end: this.selectedDateInfo.endStr
    });
  }

  setErrorMessage(){
    this.errorVisible = true;
    if(!this.selectedPredmet){
      this.errorMessage = "Predmet i vreme moraju biti izabrani";
    }
    else if(!this.selectedPredmet){
      this.errorMessage = "Predmet mora biti izabran";
    }
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    const startDate = new Date(selectInfo.startStr);
    const endDate = new Date(selectInfo.endStr);
    const rokStart = new Date(this.selectedIspitniRok.pocetak);
    const rokEnd = new Date(this.selectedIspitniRok.kraj);

    if (startDate >= rokStart && endDate <= rokEnd) {
      this.selectedDateInfo = selectInfo;
      this.kreairanjeIspitaVisible = true;
    } else {
      this.warningMessage = 'Izabrani datum je van okvira ispitnog roka.';
      this.showWarningDialog = true;    }
  }

  closeWarningDialog() {
    this.showWarningDialog = false;
  }

  handleEventClick(clickInfo: EventClickArg) {
    this.clickInfo = clickInfo;
    this.izmenaIspitaVisible = true;
  }

  handleEventDrop(eventDropInfo: EventDropArg){
    const { event } = eventDropInfo;
    const newStartDate = event.start;
    const newEndDate = event.end;

    console.log(this.selectedIspitniRok);
    if (newStartDate != null && !this.isDateInRange(newStartDate) || (newEndDate && !this.isDateInRange(newEndDate))) {
      eventDropInfo.revert();
      this.warningMessage = "Datum nije u opsegu ispitnog roka";
      this.showWarningDialog = true;
    }
  }

  isDateInRange(date: Date): boolean {
    return date >= new Date(this.selectedIspitniRok.pocetak) && date <= new Date(this.selectedIspitniRok.kraj);
  }

  handleEventChange(eventChange: EventChangeArg){
    this.sacuvajIzmeneBtnDisabled = false;
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents.set(events);
    this.changeDetector.detectChanges();
  }

  clearEvents(){
    const calendarApi = this.calendarComponent.getApi();
    calendarApi.removeAllEvents();
    if(this.selectedIspitniRok){
      calendarApi.addEvent({
        start: formatDateFromString(this.selectedIspitniRok.pocetak.toString()),
        end: formatDateFromString(this.selectedIspitniRok.kraj.toString()),
        display: 'background'
      });
    }
  }

  protected readonly String = String;
}
