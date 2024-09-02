import {ChangeDetectorRef, Component, OnInit, signal, ViewChild} from '@angular/core';
import {DropdownModule} from "primeng/dropdown";
import {FullCalendarComponent, FullCalendarModule} from "@fullcalendar/angular";
import {JsonPipe, NgForOf, NgIf} from "@angular/common";
import {CalendarOptions, EventApi, EventClickArg} from "@fullcalendar/core";
import rrulePlugin from "@fullcalendar/rrule";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import listPlugin from "@fullcalendar/list";
import {TerminNastaveService} from "../../../services/termin-nastave.service";
import {Predmet} from "../../../model/predmet";
import {TerminNastave} from "../../../model/terminNastave";
import {PredmetService} from "../../../services/predmet.service";
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NastavnikService} from "../../../services/nastavnik.service";
import {Nastavnik} from "../../../model/nastavnik";
import {HttpParams} from "@angular/common/http";
import {formatDateFromString} from "../../../utils/date-converter";
import {ButtonDirective} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {DialogModule} from "primeng/dialog";
import {InputNumberModule} from "primeng/inputnumber";
import {Fajl} from "../../../model/fajl";
import {lastValueFrom} from "rxjs";
import {GoogleDriveService} from "../../../services/google-drive.service";
import {NastavniMaterijal} from "../../../model/nastavniMaterijal";
import {Ishod} from "../../../model/ishod";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {ObrazovniCilj} from "../../../model/obrazovniCilj";

@Component({
  selector: 'app-raspored-ishoda',
  standalone: true,
  imports: [
    DropdownModule,
    FullCalendarModule,
    NgIf,
    FormsModule,
    ButtonDirective,
    CalendarModule,
    DialogModule,
    InputNumberModule,
    ReactiveFormsModule,
    NgForOf,
    JsonPipe,
    ToastModule
  ],
  templateUrl: './raspored-ishoda.component.html',
  styleUrl: './raspored-ishoda.component.css'
})
export class RasporedIshodaComponent implements OnInit{
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
    contentHeight: 700,
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

  nastavniMaterijali: NastavniMaterijal[] = [];
  selectedNastavniMaterijal!: NastavniMaterijal;

  predmeti: Predmet[] = [];
  selectedPredmet!: Predmet;

  terminiNastave: TerminNastave[] = [];

  nastavnik!: Nastavnik;

  ishodId: number = 0;
  opis!: string;

  selectedFile: File | null = null;
  uploadedFile!: Fajl;

  visible: boolean = false;

  form: FormGroup;

  constructor(
    private changeDetector: ChangeDetectorRef,
    private terminNastaveService: TerminNastaveService,
    private predmetService: PredmetService,
    private nastavnikService: NastavnikService,
    private googleDriveService: GoogleDriveService,
    private fb: FormBuilder,
    private messageService: MessageService
    ) {
    this.form = this.fb.group({
      items: this.fb.array([this.fb.control('')])
    });
  }

  get items() {
    return this.form.get('items') as FormArray;
  }

  addItem() {
    this.items.push(this.fb.control(''));
  }

  removeItem(index: number) {
    this.items.removeAt(index);
  }

  ngOnInit(): void {
    const token: string | null = localStorage.getItem('token');
    if (token) {
      const nastavnikUsername = JSON.parse(atob(token.split(".")[1])).username;
      const params: HttpParams = new HttpParams()
        .set("korisnickoIme", nastavnikUsername);

      this.nastavnikService.getByKorisnickoIme(params).subscribe(data => this.nastavnik = data);

      this.predmetService.getPredmetByNastavnik(nastavnikUsername).subscribe(data => this.predmeti = data);
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

  sacuvaj(){

    let obrazovniCiljevi: ObrazovniCilj[] = [];

    for(let item of this.form.value["items"]){
      obrazovniCiljevi.push({
        id: 0,
        opis: item
      });
    }

    console.log(this.form.value["items"]);
    const params: HttpParams = new HttpParams()
      .set("predmetId", this.selectedPredmet.id)

    const ishod: any = {
      id: 0,
      opis: this.opis,
      nastavniMaterijal: this.selectedNastavniMaterijal,
      obrazovniCiljevi: obrazovniCiljevi
    }

    lastValueFrom(this.terminNastaveService.updateIshod(Number(this.clickInfo.event.id), params, ishod))
      .then(() => {
        this.messageService.add({ severity: "success", summary: "Success", detail: "Ishod uspesno dodat", life: 1000 });
      })
      .catch((error) => {
        this.messageService.add({ severity: "error", summary: "Error", detail: "Greska", life: 1000 });
        console.error(error);
      });
  }

  otkazi(){
    this.visible = false;
  }

  loadEvents(){
    if(this.selectedPredmet && this.nastavnik.id) {
      const params: HttpParams = new HttpParams()
        .set("nastavnikId", this.nastavnik.id)
        .set("predmetId", this.selectedPredmet.id)

      this.terminNastaveService.getAllByNastavnikAndPredmet(params).subscribe((data) => {
        console.log(data);
        this.terminiNastave = data;
        for(let terminNastave of data){
          this.addEventToCalendar(terminNastave);
        }
      });
    }
  }

  addEventToCalendar(terminNastave: TerminNastave){
    const calendarApi = this.calendarComponent.getApi();

    calendarApi.addEvent({
      id: terminNastave.id?.toString(),
      start: formatDateFromString(terminNastave.vremePocetka.toString()),
      end: formatDateFromString(terminNastave.vremeZavrsetka.toString()),
      title: this.selectedPredmet.naziv,
      description: terminNastave.tipNastave?.naziv
    });
  }

  handleEventClick(clickInfo: EventClickArg){
    this.clickInfo = clickInfo;
    const terminNastave = this.terminiNastave.find(t => t.id === Number(clickInfo.event.id));

    if(terminNastave?.ishod?.id){

    }

    this.visible = true;
  }

  handleEvents(events: EventApi[]) {
    this.currentEvents.set(events);
    this.changeDetector.detectChanges();
  }

}
