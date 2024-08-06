import {ChangeDetectorRef, Component, OnInit, signal, WritableSignal} from '@angular/core';
import {FullCalendarModule} from "@fullcalendar/angular";
import {CalendarOptions, DateSelectArg, EventClickArg, EventApi, EventInput} from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import listPlugin from '@fullcalendar/list';
import { INITIAL_EVENTS, createEventId } from './event-utils';
import {NgForOf, NgIf} from "@angular/common";
import {DropdownModule} from "primeng/dropdown";
import {Predmet} from "../../../../model/predmet";
import {StudijskiProgram} from "../../../../model/studijskiProgram";
import {FormsModule} from "@angular/forms";
import {StudijskiProgramService} from "../../../../services/studijski-program.service";
import {PredmetService} from "../../../../services/predmet.service";
import {DialogModule} from "primeng/dialog";
import {ChipsModule} from "primeng/chips";
import {Button} from "primeng/button";
import {TerminNastaveService} from "../../../../services/termin-nastave.service";
import {TerminNastave} from "../../../../model/terminNastave";

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
    Button
  ],
  templateUrl: './nastava.component.html',
  styleUrl: './nastava.component.css'
})
export class NastavaComponent implements OnInit{

  events: EventInput[] = [];
  terminiNastave: TerminNastave[] = [];
  newTerminiNastave: TerminNastave[] = [];
  studijskiProgrami: StudijskiProgram[] = [];
  selectedStudijskiProgram!: StudijskiProgram;
  predmeti: Predmet[] = [];
  selectedPredmet: any;
  godineTrajanja: number[] = [];
  godina: number | null = null;
  visible: boolean = false;
  selectedDateInfo!: DateSelectArg;

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
    initialEvents: this.events, // alternatively, use the `events` setting to fetch from a feed
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    select: this.handleDateSelect.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this)
    /* you can update a remote database when these fire:
    eventAdd:
    eventChange:
    eventRemove:
    */
  });
  currentEvents = signal<EventApi[]>([]);

  constructor(
    private changeDetector: ChangeDetectorRef,
    private studijskiProgramService: StudijskiProgramService,
    private predmetService: PredmetService,
    private terminNastaveService: TerminNastaveService
  )
  {}
  ngOnInit(): void {
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
  }

  createTerminNastave(terminNastave: TerminNastave){
    if(this.selectedPredmet && this.selectedPredmet.id) {
      this.terminNastaveService.createByPredmet(this.selectedPredmet.id, terminNastave).subscribe();
    }
  }

  loadEvents() {
    for(let predmet of this.predmeti) {
      this.terminNastaveService.getAllByPredmet(predmet.id).subscribe(data => {
        this.terminiNastave = data;
        this.events = data.map(termin => ({
          title: predmet.naziv,
          start: termin.vremePocetka,
          end: termin.vremeZavrsetka
        }));
      });
    }
  }

  getGodineTrajanja(){
    this.godineTrajanja = [];
    if(this.selectedStudijskiProgram.godineTrajanja) {
      for (let i = 1; i <= this.selectedStudijskiProgram.godineTrajanja; i++) {
        this.godineTrajanja.push(i);
      }
    }
  }

  getPredmeti(studijskiProgramId: number){
    if(this.godina){
      this.predmetService.getPredmetByStudijskiProgramAndGodina(studijskiProgramId, this.godina).subscribe(data => {
        this.predmeti = data;
        this.loadEvents();
        console.log(this.events);
      });
    }
  }

  handleCalendarToggle() {
    this.calendarVisible.update((bool) => !bool);
  }

  handleWeekendsToggle() {
    this.calendarOptions.update((options) => ({
      ...options,
      weekends: !options.weekends,
    }));
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    this.selectedDateInfo = selectInfo;
    this.visible = true;
  }

  dialogSave(){
    const calendarApi = this.selectedDateInfo.view.calendar;
    //dodavati eventove preko calendarApi pri odabriu predmeta
    //formatirati datum
    if(this.selectedPredmet) {
      calendarApi.addEvent({
        title: this.selectedPredmet.naziv,
        start: this.selectedDateInfo.startStr,
        end: this.selectedDateInfo.endStr,
        allDay: this.selectedDateInfo.allDay
      });
      let terminNastave: TerminNastave = {
        id: 0,
        ishod: null,
        tipNastave: null,
        vremePocetka: new Date(this.selectedDateInfo.startStr),
        vremeZavrsetka: new Date(this.selectedDateInfo.endStr),
        nastavniMaterijal: null
      }
      this.createTerminNastave(terminNastave);
    }
    this.selectedPredmet = null;
    this.visible = false;
  }

  handleEventClick(clickInfo: EventClickArg) {
    if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
      clickInfo.event.remove();
    }
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents.set(events);
    this.changeDetector.detectChanges(); // workaround for pressionChangedAfterItHasBeenCheckedError
  }

}
