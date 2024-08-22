import {ChangeDetectorRef, Component, OnInit, signal, ViewChild} from '@angular/core';
import {FullCalendarComponent, FullCalendarModule} from "@fullcalendar/angular";
import {CalendarOptions, DateSelectArg, EventApi, EventClickArg, EventInput, Calendar, EventChangeArg} from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import listPlugin from '@fullcalendar/list';
import {NgForOf, NgIf} from "@angular/common";
import {DropdownModule} from "primeng/dropdown";
import {Predmet} from "../../../../model/predmet";
import {StudijskiProgram} from "../../../../model/studijskiProgram";
import {FormControl, FormGroup, FormsModule, Validators} from "@angular/forms";
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
import rrulePlugin from '@fullcalendar/rrule'
import { RRule } from 'rrule';
import {CalendarModule} from "primeng/calendar";
import {InputSwitchModule} from "primeng/inputswitch";
import {EditorModule} from "primeng/editor";
import {ToggleButtonModule} from "primeng/togglebutton";
import {PaginatorModule} from "primeng/paginator";
import {jsDate2Date} from "../../../../utils/jsDate2Date";
import {lastValueFrom} from "rxjs";

@Component({
  selector: 'app-raspored-nastave',
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
    ToastModule,
    CalendarModule,
    InputSwitchModule,
    EditorModule,
    ToggleButtonModule,
    PaginatorModule
  ],
  templateUrl: './nastava.component.html',
  styleUrl: './nastava.component.css'
})
export class NastavaComponent implements OnInit {

  studijskiProgrami: StudijskiProgram[] = [];
  selectedStudijskiProgram!: StudijskiProgram;
  semestri: any[] = [
    {redniBrojSemestra: 1},
    {redniBrojSemestra: 2}
  ];
  selectedSemestar!: any;
  predmeti: Predmet[] = [];
  selectedPredmet!: any;
  godina: number | null = null;
  visible: boolean = false;
  selectedDateInfo!: DateSelectArg;
  tipoviNastave: TipNastave[] = [];
  selectedTipNastave!: any;
  deleteNastavaVisible: boolean = false;
  clickInfo!: EventClickArg;
  title: string = '';
  dateStart!: Date;
  timeStart!: Date;
  dateEnd!: Date;
  timeEnd!: Date;
  opcije = [
    { id: 0, naziv: "Ne ponavlja se", sifra: ""},
    { id: 1, naziv: "Radnim Danima", sifra: ""},
    { id: 2, naziv: "Dnevno", sifra: "DAILY"},
    { id: 3, naziv: "Nedeljno", sifra: "WEEKLY"},
    { id: 4, naziv: "Mesecno", sifra: "MONTHLY"},
    { id: 5, naziv: "Godisnje", sifra: "YEARLY"},
  ];
  selectedOpcija: any = {};
  allDay: boolean = false;
  detalji: string = '';
  recurranceDialog: boolean = false;
  krajPonavljanjaTermina!: Date;
  checkedSu: boolean = false;
  checkedMo: boolean = false;
  checkedTu: boolean = false;
  checkedWe: boolean = false;
  checkedTh: boolean = false;
  checkedFr: boolean = false;
  checkedSa: boolean = false;
  brojPonavljanja: number = 1;
  text: string = "";
  rruleDays: string[] = [];
  rruleObject: any = {};

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
    contentHeight: 600,
    initialEvents: [],
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    eventChange: this.handleEventChange.bind(this),
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
  ) {
  }

  ngOnInit(): void {
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
    this.tipNastaveService.getAll().subscribe(data => this.tipoviNastave = data);
  }

  async loadEvents(){
    const calendarApi = this.calendarComponent.getApi();

    for(let predmet of this.predmeti) {
      await this.terminNastaveService.getAllByPredmet(predmet.id)
        .subscribe(data =>  {
          for(let terminNastave of data){
            calendarApi.addEvent({
              id: terminNastave.id?.toString(),
              title: predmet.naziv,
              description: terminNastave.tipNastave?.naziv,
              start: formatDateFromString(terminNastave.vremePocetka.toString()),
              end: formatDateFromString(terminNastave.vremeZavrsetka.toString())
            });
          }
        });
    }
  }

  getPredmeti(){
    if(this.selectedStudijskiProgram && this.selectedSemestar) {
      this.clearEvents();
      this.predmetService.getPredmetByStudijskiProgramAndSemestar(this.selectedStudijskiProgram.id, this.selectedSemestar.redniBrojSemestra)
        .subscribe(data => {
          this.predmeti = data;
          this.loadEvents();
        });
    }
  }

  clearEvents(){
    const calendarApi = this.calendarComponent.getApi();
    calendarApi.removeAllEvents();
  }

  reloadRecurranceDialog(){
    this.checkedSu = false;
    this.checkedMo = false;
    this.checkedTu = false;
    this.checkedWe = false;
    this.checkedTh = false;
    this.checkedFr = false;
    this.checkedSa = false;

    if(this.selectedOpcija.id !== 0) {
      if (this.dateStart.getDay() === 1) {
        this.checkedMo = true;
      }
      if (this.dateStart.getDay() === 2) {
        this.checkedTu = true;
      }
      if (this.dateStart.getDay() === 3) {
        this.checkedWe = true;
      }
      if (this.dateStart.getDay() === 4) {
        this.checkedTh = true;
      }
      if (this.dateStart.getDay() === 5) {
        this.checkedFr = true;
      }
      if (this.dateStart.getDay() === 6) {
        this.checkedSa = true;
      }
      if (this.dateStart.getDay() === 7) {
        this.checkedSu = true;
      }
      this.changeText();
    }
  }

  showRecurranceDialog(){
    this.reloadRecurranceDialog();
    this.recurranceDialog = true;
  }

  changeText(){
    this.rruleDays = [];
    this.text = "";
    this.text = "Dani kojima se termin ponavlja su: "

    if(this.checkedSu){
      this.text = this.text + "Nedelja, ";
      this.rruleDays.push("su");
    }
    if(this.checkedMo){
      this.text = this.text + "Ponedeljak, ";
      this.rruleDays.push("mo");
    }
    if(this.checkedTu){
      this.text = this.text + "Utorak, ";
      this.rruleDays.push("tu");
    }
    if(this.checkedWe){
      this.text = this.text + "Sreda, ";
      this.rruleDays.push("we");
    }
    if(this.checkedTh){
      this.text = this.text + "Cetvrtak, ";
      this.rruleDays.push("th");
    }
    if(this.checkedFr){
      this.text = this.text + "Petak, ";
      this.rruleDays.push("fr");
    }
    if(this.checkedSa){
      this.text = this.text + "Subota, ";
      this.rruleDays.push("sa");
    }

    this.text = this.text.slice(0, -1) + ` pocevsi od ${this.dateStart.toDateString()}`;
  }

  recurranceDialogSacuvaj(){
    this.rruleObject = {
      freq: this.selectedOpcija.sifra,
      interval: this.brojPonavljanja,
      byweekday: this.rruleDays,
      dtstart: this.dateStart.toISOString(),
      until: jsDate2Date(this.krajPonavljanjaTermina)
    }
    this.recurranceDialog = false;
  }

  closeRecurranceDialog(){
    this.recurranceDialog = false;
    this.selectedOpcija = this.opcije.find(opcija => opcija.id === 0);
  }

  closeDialog(){
    this.visible = false;
  }
  //TODO: resetovanje
  sacuvaj(){
    const calendarApi = this.calendarComponent.getApi();
    if(this.selectedPredmet && this.selectedTipNastave && this.rruleObject) {
      let duration: number = this.dateEnd.getHours()-this.dateStart.getHours();
      const event: EventInput = {
        title: `${this.selectedPredmet.naziv}`,
        description: this.selectedTipNastave.naziv,
        duration: 1000 * 60 * 60 * duration,
        rrule: this.rruleObject
      }
      calendarApi.addEvent(event);
      const terminNastave: TerminNastave = {
        id: 0,
        tipNastave: this.selectedTipNastave,
        vremePocetka: this.dateStart,
        ishod: null,
        nastavniMaterijal: null,
        vremeZavrsetka: this.dateEnd,
        event: {
          ...this.rruleObject,
          duration: duration
        }
      };
      this.createTerminNastave(terminNastave)
        .then((createdTerminiNastave: TerminNastave[]) => {
          this.messageService.add({severity: "success", summary: "Success", detail: "Termini nastave uspesno dodati", life: 1000})
        })
        .catch((error) => {
          console.error(error.message);
        })
      this.visible = false;
    }
  }

  async createTerminNastave(terminNastave: TerminNastave): Promise<any>{
    try{
      return await lastValueFrom(this.terminNastaveService.createByPredmet(this.selectedPredmet.id, terminNastave));
    }
    catch (error: any){
      throw new Error(error.message || error.toString());
    }
  }

  checkSelectedOpcija(){
    if(this.selectedOpcija == this.opcije.find(opcija => opcija.id === 0)){
      this.closeRecurranceDialog();
    }
  }

  changeAllDay(){
    console.log(this.allDay);
  }

  handleEventChange(eventChange: EventChangeArg) {
  }

  handleDateSelect(selectInfo: DateSelectArg) {
    this.selectedDateInfo = selectInfo;
    this.dateStart = selectInfo.start;
    this.dateEnd = selectInfo.end;
    this.timeStart = selectInfo.start;
    this.timeEnd = selectInfo.end;
    this.visible = true;
  }

  handleEventClick(clickInfo: EventClickArg) {
    this.clickInfo = clickInfo;
    this.deleteNastavaVisible = true;
  }

  deleteTerminNastave(event: any){
    this.confirmationService.confirm({
      acceptLabel: "Da",
      rejectLabel: "Ne",
      target: event.target,
      message: 'Da li ste sigurni da zelite da obrisete ovaj termin nastave?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.terminNastaveService.delete(Number(this.clickInfo.event.id))
          .subscribe({
            next: () => {
              this.clearEvents();
              this.loadEvents();
              this.deleteNastavaVisible = false;
              this.messageService.add({severity: "success", summary: "Success", detail: "Termini nastave uspesno obrisan", life: 1000})
            },
            error: (error: any) => {
              this.messageService.add({severity: "error", summary: "Error", detail: error.message.toString(), life: 1000})
            }
          });
      },
      reject: () => {
        this.messageService.add({severity:'error', summary:'Rejected', detail:'Operacija prekinuta', life: 1000});
      }
    });
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents.set(events);
    this.changeDetector.detectChanges();
  }

  protected readonly String = String;
}
