import {ChangeDetectorRef, Component, OnInit, signal, ViewChild} from '@angular/core';
import {FullCalendarComponent, FullCalendarModule} from "@fullcalendar/angular";
import {CalendarOptions, DateSelectArg, EventApi, EventClickArg, EventInput, Calendar} from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import listPlugin from '@fullcalendar/list';
import {NgForOf, NgIf} from "@angular/common";
import {DropdownModule} from "primeng/dropdown";
import {Predmet} from "../../../../model/predmet";
import {StudijskiProgram} from "../../../../model/studijskiProgram";
import {FormControl, FormsModule} from "@angular/forms";
import {StudijskiProgramService} from "../../../../services/studijski-program.service";
import {PredmetService} from "../../../../services/predmet.service";
import {DialogModule} from "primeng/dialog";
import {ChipsModule} from "primeng/chips";
import {Button, ButtonDirective} from "primeng/button";
import {TerminNastaveService} from "../../../../services/termin-nastave.service";
import {TerminNastave} from "../../../../model/terminNastave";
import {formatDateFromString} from "../../../../utils/date-converter"
import {Ripple} from "primeng/ripple";
import {ConfirmationService, MessageService} from "primeng/api";
import {ConfirmPopupModule} from 'primeng/confirmpopup';
import {ToastModule} from "primeng/toast";
import {TipNastave} from "../../../../model/tipNastave";
import {TipNastaveService} from "../../../../services/tip-nastave.service";

@Component({
  selector: 'app-nastava',
  standalone: true,
  imports: [
    FullCalendarModule,
    NgIf,
    NgForOf,
    DropdownModule,
    FormsModule,
    DialogModule,
    ChipsModule,
    Button,
    ButtonDirective,
    Ripple,
    ConfirmPopupModule,
    ToastModule
  ],
  templateUrl: './nastava.component.html',
  styleUrl: './nastava.component.css'
})
export class NastavaComponent implements OnInit{

  studijskiProgrami: StudijskiProgram[] = [];
  selectedStudijskiProgram!: StudijskiProgram;
  predmeti: Predmet[] = [];
  selectedPredmet!: any;
  godineTrajanja: number[] = [];
  godina: number | null = null;
  visible: boolean = false;
  selectedDateInfo!: DateSelectArg;
  tipoviNastave: TipNastave[] = [];
  selectedTipNastave!: any;
  errorVisible: boolean = false;
  errorMessage: string = "";

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
    initialEvents: [],
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    select: this.handleDateSelect.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this)
  });
  currentEvents = signal<EventApi[]>([]);

  constructor(
    private changeDetector: ChangeDetectorRef,
    private studijskiProgramService: StudijskiProgramService,
    private predmetService: PredmetService,
    private terminNastaveService: TerminNastaveService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private tipNastaveService: TipNastaveService
  )
  {}
  ngOnInit(): void {
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
    this.tipNastaveService.getAll().subscribe(data => this.tipoviNastave = data);
  }

  async createTerminNastave(terminNastave: TerminNastave): Promise<any> {
    if (this.selectedPredmet && this.selectedPredmet.id) {
      try {
        return await this.terminNastaveService.createByPredmet(this.selectedPredmet.id, terminNastave).toPromise();
      } catch (error: any) {
        throw new Error(error.message || error.toString());
      }
    } else {
      throw new Error('Izabrani predmet nije definisan ili nema id');
    }
  }

  async loadEvents() {
    const calendarApi = this.calendarComponent.getApi();

    for(let predmet of this.predmeti) {
      await this.terminNastaveService.getAllByPredmet(predmet.id).subscribe(data => {
        for(let terminNastave of data){
          calendarApi.addEvent({
            id: terminNastave.id?.toString(),
            title: predmet.naziv + "\n" + terminNastave.tipNastave?.naziv,
            start: formatDateFromString(terminNastave.vremePocetka.toString()),
            end: formatDateFromString(terminNastave.vremeZavrsetka.toString()),
          });
        }
      });
    }
  }

  getGodineTrajanja(){
    this.clearEvents();
    this.godineTrajanja = [];
    if(this.selectedStudijskiProgram.godineTrajanja) {
      for (let i = 1; i <= this.selectedStudijskiProgram.godineTrajanja; i++) {
        this.godineTrajanja.push(i);
      }
    }
  }

  getPredmeti(studijskiProgramId: number){
    this.clearEvents();
    if(this.godina){
      this.predmetService.getPredmetByStudijskiProgramAndGodina(studijskiProgramId, this.godina).subscribe( data => {
        this.predmeti = data;
        this.loadEvents();
      });
    }
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
        for(let event of calendarApi.getEvents()){
          let terminNastave: TerminNastave = {
            id: null,
            ishod: null,
            tipNastave: null,
            vremePocetka: new Date(event.startStr),
            vremeZavrsetka: new Date(event.endStr),
            nastavniMaterijal: null
          }
          this.terminNastaveService.update(Number(event.id), terminNastave).subscribe();
        }
        this.messageService.add({severity:'success', summary:'Confirmed', detail:'Uspesno sacuvano'});
      },
      reject: () => {
        this.messageService.add({severity:'error', summary:'Rejected', detail:'Operacija prekinuta'});
      }
    });
  }

  dialogSave() {
    const calendarApi = this.selectedDateInfo.view.calendar;

    if (!this.selectedPredmet || !this.selectedTipNastave) {
      this.setErrorMessage();
      return;
    }

    const terminNastave: TerminNastave = {
      id: 0,
      ishod: null,
      tipNastave: this.selectedTipNastave,
      vremePocetka: new Date(this.selectedDateInfo.startStr),
      vremeZavrsetka: new Date(this.selectedDateInfo.endStr),
      nastavniMaterijal: null
    };

    this.createTerminNastave(terminNastave)
      .then((createdTerminNastave: TerminNastave) => {
        this.addEventToCalendar(calendarApi, createdTerminNastave.id);
        this.resetForm();
      })
      .catch((error) => {
        console.error(error);
      });
  }

  setErrorMessage() {
    this.errorVisible = true;
    if (!this.selectedPredmet && !this.selectedTipNastave) {
      this.errorMessage = "Predmet i Tip nastave moraju biti izabrani";
    } else if (!this.selectedPredmet) {
      this.errorMessage = "Predmet mora biti izabran";
    } else if (!this.selectedTipNastave) {
      this.errorMessage = "Tip nastave mora biti izabran";
    }
  }

  addEventToCalendar(calendarApi: any, eventId: any) {
    calendarApi.addEvent({
      id: eventId,
      title: `${this.selectedPredmet.naziv}\n${this.selectedTipNastave.naziv}`,
      start: this.selectedDateInfo.startStr,
      end: this.selectedDateInfo.endStr,
      allDay: this.selectedDateInfo.allDay
    });
  }

  resetForm() {
    this.selectedPredmet = null;
    this.selectedTipNastave = null;
    this.visible = false;
    this.errorVisible = false;
    this.errorMessage = "";
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    this.selectedDateInfo = selectInfo;
    this.visible = true;
  }

  handleEventClick(clickInfo: EventClickArg) {
    if (confirm(`Da li ste sigurni da zelite da obrisete ovaj dogadjaj? \n '${clickInfo.event.title}'`)) {
      clickInfo.event.remove();
      this.terminNastaveService.delete(Number(clickInfo.event.id)).subscribe();
    }
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents.set(events);
    this.changeDetector.detectChanges();
  }

  clearEvents(){
    const calendarApi = this.calendarComponent.getApi();
    calendarApi.removeAllEvents();
  }

}
