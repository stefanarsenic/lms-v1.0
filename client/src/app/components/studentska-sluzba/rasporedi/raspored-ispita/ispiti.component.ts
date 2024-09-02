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
import {Button} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {CalendarModule} from "primeng/calendar";
import {HttpParams} from "@angular/common/http";
import {ConfirmationService, MessageService} from "primeng/api";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {ToastModule} from "primeng/toast";
import {formatTimeFromString} from "../../../../utils/time-converter";
import {Ripple} from "primeng/ripple";
import {EvaluacijaZnanjaService} from "../../../../services/evaluacija-znanja.service";
import {EvaluacijaZnanja} from "../../../../model/evaluacijaZnanja";
import {InputNumberModule} from "primeng/inputnumber";
import {TipEvaluacije} from "../../../../model/tipEvaluacije";
import {TipEvaluacijeService} from "../../../../services/tip-evaluacije.service";
import {lastValueFrom} from "rxjs";

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
    Ripple,
    InputNumberModule
  ],
  templateUrl: './ispiti.component.html',
  styleUrl: './ispiti.component.css'
})
export class IspitiComponent implements OnInit{

  ispiti: EvaluacijaZnanja[] = [];

  tipoviEvaluacije: TipEvaluacije[] = [];
  selectedTipEvaluacije!: any;

  predmeti: Predmet[] = [];
  selectedPredmet!: any;

  studijskiProgrami: StudijskiProgram[] = [];
  selectedStudijskiProgram!: StudijskiProgram;

  ispitniRokovi: IspitniRok[] = [];
  selectedIspitniRok!: IspitniRok;

  selectedDateInfo!: DateSelectArg;
  clickInfo!: EventClickArg;

  visible: boolean = false;
  errorVisible: boolean = false;
  showWarningDialog: boolean = false;
  deleteEvaluacijaVisible: boolean = false;

  errorMessage: string = "";
  warningMessage: string = '';

  pocetak!: Date;
  kraj!: Date;
  bodovi!: number;
  opis: string = "";

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
    eventsSet: this.handleEvents.bind(this)
  });
  currentEvents = signal<EventApi[]>([]);

  constructor(
    private ispitniRokService: IspitniRokService,
    private evaluacijaZnanjaService: EvaluacijaZnanjaService,
    private changeDetector: ChangeDetectorRef,
    private predmetService: PredmetService,
    private studijskiProgramService: StudijskiProgramService,
    private tipEvaluacijeService: TipEvaluacijeService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
    ) {}

  ngOnInit(): void {
    this.tipEvaluacijeService.getAll().subscribe((data) => {
      this.selectedTipEvaluacije = data.find(t => t.id === 2);
    });
    this.ispitniRokService.getAll().subscribe(data => this.ispitniRokovi = data);
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
  }

  async loadEvents() {
    const calendarApi = this.calendarComponent.getApi();

    let params: HttpParams = new HttpParams()
      .set("studijskiProgramId", this.selectedStudijskiProgram.id)
      .set("ispitniRokId", this.selectedIspitniRok.id);

    this.evaluacijaZnanjaService.getAllIspitiByStudijskiProgram(params).subscribe(
      data => {
        this.ispiti = data;
        for(let evaluacijaZnanja of data){
          calendarApi.addEvent({
            id: evaluacijaZnanja.id.toString(),
            title: `${evaluacijaZnanja.realizacijaPredmeta?.predmet.naziv}`,
            start: formatDateFromString(evaluacijaZnanja.vremePocetka.toString()),
            end: formatDateFromString(evaluacijaZnanja.vremeZavrsetka.toString()),
          });
        }
      }
    )
  }

  async createIspit(evaluacijaZnanja: EvaluacijaZnanja): Promise<any>{
    const params = new HttpParams()
      .set('predmetId', this.selectedPredmet.id)
      .set('ispitniRokId', this.selectedIspitniRok.id)
      .set('studijskiProgramId', this.selectedStudijskiProgram.id);

    try {
      return await lastValueFrom(this.evaluacijaZnanjaService.createIspit(evaluacijaZnanja, params));
    }
    catch (error: any) {
      throw new Error(error.message || error.toString());
    }
  }

  sacuvaj(){
    const calendarApi = this.calendarComponent.getApi();

    if(!this.selectedPredmet){
      this.setErrorMessage();
      return
    }

    const evaluacijaZnanja: EvaluacijaZnanja = {
      id: 0,
      vremePocetka: this.pocetak,
      vremeZavrsetka: this.kraj,
      bodovi: this.bodovi,
      ishod: {
        id: 0,
        predmet: this.selectedPredmet,
        opis: this.opis
      },
      tipEvaluacije: this.selectedTipEvaluacije
    }

    this.createIspit(evaluacijaZnanja)
      .then(
        (createdIspit: EvaluacijaZnanja) => {
          this.getPredmeti();
          this.addEventToCalendar(calendarApi, createdIspit.id);
          this.resetForm();
          this.messageService.add({severity:'success', summary:'Confirmed', detail:'Kreiranje uspesno'});
        })
      .catch((error) => {
        console.error(error);
      });
  }

  closeDialog(){
    this.visible = false;
  }

  deleteEvaluacijaZnanja(event: any){
    this.confirmationService.confirm({
      acceptLabel: "Da",
      rejectLabel: "Ne",
      target: event.target,
      message: "Da li ste sigurni da zelite da obrisete ovu evaluaciju znanja?",
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.evaluacijaZnanjaService.delete(Number(this.clickInfo.event.id))
          .subscribe({
            next: () => {
              this.clearEvents();
              this.loadEvents();
              this.deleteEvaluacijaVisible = false;
              this.messageService.add({
                severity: "success",
                summary: "Success",
                detail: "Evaluacija znanja uspesno obrisana",
                life: 1000
              });
            },
            error: (error: any) => {
              this.messageService.add({
                severity: "error",
                summary: "Error",
                detail: error.message.toString(),
                life: 1000
              });
            }
          });

      },
      reject: () => {
        this.messageService.add({severity:'error', summary:'Rejected', detail:'Operacija prekinuta', life: 1000});
      }
    });
  }

  resetujKalendar(){
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
      this.predmetService.getPredmetiByStudijskiProgram(this.selectedStudijskiProgram.id).subscribe( data => {
        this.predmeti = data;
      });
    }
  }

  selektujIspitniRok(){
    this.clearEvents();
    this.loadEvents();
  }

  resetForm(){
    this.selectedPredmet = null;
    this.errorMessage = "";
    this.errorVisible = false;
    this.visible = false;
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
      this.visible = true;
      this.pocetak = startDate;
      this.kraj = endDate;
    } else {
      this.warningMessage = "Datum nije u opsegu ispitnog roka";
      this.showWarningDialog = true;    }
  }

  closeWarningDialog() {
    this.showWarningDialog = false;
  }

  handleEventClick(clickInfo: EventClickArg) {
    this.clickInfo = clickInfo;
    this.deleteEvaluacijaVisible = true;
  }

  handleEventDrop(eventDropInfo: EventDropArg){
    const { event } = eventDropInfo;
    const newStartDate = event.start;
    const newEndDate = event.end;

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
    const evaluacijaZnanja = this.ispiti.find(e => e.id = Number(eventChange.event.id));
    if(evaluacijaZnanja){
      evaluacijaZnanja.vremePocetka = <Date>eventChange.event.start;
      evaluacijaZnanja.vremeZavrsetka = <Date>eventChange.event.end;

      this.evaluacijaZnanjaService.update(Number(eventChange.event.id), evaluacijaZnanja).subscribe({
        next: () => {
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Evaluacija znanja uspesno izmenjena",
            life: 1000
          });        },
        error: (error: any) => {
          this.messageService.add({
            severity: "error",
            summary: "Error",
            detail: error.message,
            life: 1000
          });
        }
      });
    }
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
