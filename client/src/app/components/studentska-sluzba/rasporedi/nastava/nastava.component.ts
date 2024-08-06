import {ChangeDetectorRef, Component, OnInit, signal} from '@angular/core';
import {FullCalendarModule} from "@fullcalendar/angular";
import {CalendarOptions, DateSelectArg, EventApi, EventClickArg, EventInput} from '@fullcalendar/core';
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
import {Button} from "primeng/button";
import {TerminNastaveService} from "../../../../services/termin-nastave.service";
import {TerminNastave} from "../../../../model/terminNastave";
import {formatDateFromString} from "./date-converter"

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
    for(let predmet of this.predmeti) {
      await this.terminNastaveService.getAllByPredmet(predmet.id).subscribe(data => {
        for(let object of data){
          this.terminiNastave.push(object);
          this.events.push({
            title: predmet.naziv,
            start: formatDateFromString(object.vremePocetka.toString()),
            end: formatDateFromString(object.vremeZavrsetka.toString())
          });
        }
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
        this.loadEvents().then(r => {
          console.log(this.events);
          this.calendarOptions
          //loadovati eventove na kalendar
        });
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

  handleEventClick(clickInfo: EventClickArg) {
    if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
      clickInfo.event.remove();
      this.terminNastaveService.delete(Number(clickInfo.event.id)).subscribe();
    }
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents.set(events);
    this.changeDetector.detectChanges(); // workaround for pressionChangedAfterItHasBeenCheckedError
  }

}
