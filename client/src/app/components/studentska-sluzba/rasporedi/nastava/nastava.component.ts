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
import {FormsModule} from "@angular/forms";
import {StudijskiProgramService} from "../../../../services/studijski-program.service";
import {PredmetService} from "../../../../services/predmet.service";
import {DialogModule} from "primeng/dialog";
import {ChipsModule} from "primeng/chips";
import {Button, ButtonDirective} from "primeng/button";
import {TerminNastaveService} from "../../../../services/termin-nastave.service";
import {TerminNastave} from "../../../../model/terminNastave";
import {formatDateFromString} from "./date-converter"
import {Ripple} from "primeng/ripple";
import {ConfirmationService, MessageService} from "primeng/api";
import {ConfirmPopupModule} from 'primeng/confirmpopup';
import {ToastModule} from "primeng/toast";

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
  selectedPredmet: any;
  godineTrajanja: number[] = [];
  godina: number | null = null;
  visible: boolean = false;
  selectedDateInfo!: DateSelectArg;

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
    private messageService: MessageService
  )
  {}
  ngOnInit(): void {
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
  }

  async createTerminNastave(terminNastave: TerminNastave): Promise<any> {
    if (this.selectedPredmet && this.selectedPredmet.id) {
      try {
        return await this.terminNastaveService.createByPredmet(this.selectedPredmet.id, terminNastave).toPromise();
      } catch (error: any) {
        throw new Error(error.message || error.toString());
      }
    } else {
      throw new Error('Selected Predmet is not defined or does not have an id');
    }
  }

  async loadEvents() {
    const calendarApi = this.calendarComponent.getApi();

    for(let predmet of this.predmeti) {
      await this.terminNastaveService.getAllByPredmet(predmet.id).subscribe(data => {
        for(let object of data){
          calendarApi.addEvent({
            id: object.id?.toString(),
            title: predmet.naziv,
            start: formatDateFromString(object.vremePocetka.toString()),
            end: formatDateFromString(object.vremeZavrsetka.toString())
          })
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

  handleDateSelect(selectInfo: DateSelectArg) {
    this.selectedDateInfo = selectInfo;
    this.visible = true;
  }

  dialogSave(){
    const calendarApi = this.selectedDateInfo.view.calendar;

    let terminNastave: TerminNastave = {
      id: 0,
      ishod: null,
      tipNastave: null,
      vremePocetka: new Date(this.selectedDateInfo.startStr),
      vremeZavrsetka: new Date(this.selectedDateInfo.endStr),
      nastavniMaterijal: null
    }
    this.createTerminNastave(terminNastave)
      .then((createdTerminNastave: any) => {
        console.log(createdTerminNastave);
        if(this.selectedPredmet) {
          calendarApi.addEvent({
            id: createdTerminNastave.id,
            title: this.selectedPredmet.naziv,
            start: this.selectedDateInfo.startStr,
            end: this.selectedDateInfo.endStr,
            allDay: this.selectedDateInfo.allDay
          });
        }
        this.selectedPredmet = null;
        this.visible = false;
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
