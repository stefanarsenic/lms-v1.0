import {ChangeDetectorRef, Component, OnInit, signal, ViewChild} from '@angular/core';
import {FullCalendarComponent, FullCalendarModule} from "@fullcalendar/angular";
import {
  CalendarOptions,
  DateSelectArg,
  EventApi,
  EventChangeArg,
  EventClickArg,
  EventDropArg,
} from "@fullcalendar/core";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import listPlugin from "@fullcalendar/list";
import rrulePlugin from "@fullcalendar/rrule";
import {NgIf} from "@angular/common";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {DropdownModule} from "primeng/dropdown";
import {ToastModule} from "primeng/toast";
import {StudijskiProgramService} from "../../../../services/studijski-program.service";
import {StudijskiProgram} from "../../../../model/studijskiProgram";
import {PredmetService} from "../../../../services/predmet.service";
import {Predmet} from "../../../../model/predmet";
import {formatDateFromString} from "../../../../utils/date-converter";
import {EvaluacijaZnanja} from "../../../../model/evaluacijaZnanja";
import {EvaluacijaZnanjaService} from "../../../../services/evaluacija-znanja.service";
import {FormsModule} from "@angular/forms";
import {SemestarService} from "../../../../services/semestar.service";
import {Semestar} from "../../../../model/semestar";
import {DialogModule} from "primeng/dialog";
import {CalendarModule} from "primeng/calendar";
import {PaginatorModule} from "primeng/paginator";
import {TipEvaluacije} from "../../../../model/tipEvaluacije";
import {EditorModule} from "primeng/editor";
import {TipEvaluacijeService} from "../../../../services/tip-evaluacije.service";
import {GoogleDriveService} from "../../../../services/google-drive.service";
import {lastValueFrom} from "rxjs";
import {ConfirmationService, MessageService} from "primeng/api";
import {Fajl} from "../../../../model/fajl";
import {HttpParams} from "@angular/common/http";
import {InputSwitchModule} from "primeng/inputswitch";

@Component({
  selector: 'app-raspored-evaluacija-znanja',
  standalone: true,
  imports: [
    FullCalendarModule,
    NgIf,
    ConfirmPopupModule,
    DropdownModule,
    ToastModule,
    FormsModule,
    DialogModule,
    CalendarModule,
    PaginatorModule,
    EditorModule,
    InputSwitchModule
  ],
  templateUrl: './raspored-evaluacija-znanja.component.html',
  styleUrl: './raspored-evaluacija-znanja.component.css'
})
export class RasporedEvaluacijaZnanjaComponent implements OnInit{
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
    eventChange: this.handleEventChange.bind(this),
    select: this.handleDateSelect.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this)
  });
  currentEvents = signal<EventApi[]>([]);


  evaluacijeZnanja: EvaluacijaZnanja[] = [];

  studijskiProgrami: StudijskiProgram[] = [];
  selectedStudijskiProgram!: StudijskiProgram;

  predmeti: Predmet[] = [];
  selectedPredmet!: Predmet;

  semestri: Semestar[] = [];
  selectedSemestar!: Semestar;

  pocetak!: Date;
  kraj!: Date;
  bodovi: number = 0;
  tipoviEvaluacije: TipEvaluacije[] = [];
  selectedTipEvaluacije!: TipEvaluacije;
  opis: string = "";

  deleteEvaluacijaVisible: boolean = false;
  visible: boolean = false;

  clickInfo!: EventClickArg;

  constructor(
    private changeDetector: ChangeDetectorRef,
    private studijskiProgramService: StudijskiProgramService,
    private predmetService: PredmetService,
    private evaluacijaZnanjaService: EvaluacijaZnanjaService,
    private semestarService: SemestarService,
    private tipEvaluacijeService: TipEvaluacijeService,
    private googleDriveService: GoogleDriveService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
  }

  selectedFile: File | null = null;
  uploadedFile!: Fajl;

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  async uploadFile(): Promise<any> {
    if (this.selectedFile) {
      return lastValueFrom(this.googleDriveService.uploadFile(this.selectedFile));
    }
    return null;
  }

  ngOnInit(): void {
    this.studijskiProgramService.getAll().subscribe(data => this.studijskiProgrami = data);
    this.semestarService.getAll().subscribe(data => this.semestri = data);
    this.tipEvaluacijeService.getAll().subscribe(data => this.tipoviEvaluacije = data);
  }

  async createEvaluacijaZnanja(evaluacijaZnanja: EvaluacijaZnanja): Promise<any>{
    const params = new HttpParams()
      .set('fajlSifra', this.uploadedFile.sifra);
    return await lastValueFrom(this.evaluacijaZnanjaService.createByPredmet(this.selectedPredmet.id, evaluacijaZnanja, params));
  }

  async sacuvaj() {
    try {
      this.uploadedFile = await this.uploadFile();

      const calendarApi = this.calendarComponent.getApi();

      if (this.selectedPredmet && this.selectedTipEvaluacije) {
        const evaluacijaZnanja: EvaluacijaZnanja = {
          id: 0,
          vremePocetka: this.pocetak,
          vremeZavrsetka: this.kraj,
          tipEvaluacije: this.selectedTipEvaluacije,
          bodovi: this.bodovi,
          ishod: {
            id: 0,
            opis: this.opis,
            predmet: this.selectedPredmet
          }
        };

        const createdEvaluacijaZnanja = await this.createEvaluacijaZnanja(evaluacijaZnanja);

        this.visible = false;
        calendarApi.addEvent({
          id: createdEvaluacijaZnanja.id.toString(),
          start: formatDateFromString(createdEvaluacijaZnanja.vremePocetka.toString()),
          end: formatDateFromString(createdEvaluacijaZnanja.vremeZavrsetka.toString()),
          title: this.selectedPredmet.naziv
        });

        this.messageService.add({ severity: "success", summary: "Success", detail: "Evaluacija znanja uspesno dodata", life: 1000 });
      }
    } catch (error: any) {
      console.error(error);
      this.messageService.add({ severity: "error", summary: "Error", detail: error.message, life: 1000 });
    }
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

  closeDialog(){
    this.visible = false;
  }

  async loadEvents(){
    this.clearEvents();
    const calendarApi = this.calendarComponent.getApi();

    for(let predmet of this.predmeti) {
      await this.evaluacijaZnanjaService.getAllByPredmet(predmet.id)
        .subscribe((data: EvaluacijaZnanja[]) =>  {
          this.evaluacijeZnanja = data;
          for(let evaluacijaZnanja of data){
            calendarApi.addEvent({
              id: evaluacijaZnanja.id?.toString(),
              title: predmet.naziv,
              description: evaluacijaZnanja.tipEvaluacije.naziv,
              start: formatDateFromString(evaluacijaZnanja.vremePocetka.toString()),
              end: formatDateFromString(evaluacijaZnanja.vremeZavrsetka.toString())
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

  handleDateSelect(selectInfo: DateSelectArg) {
    this.visible = true;
    this.pocetak = selectInfo.start;
    this.kraj = selectInfo.end;
  }

  handleEventClick(clickInfo: EventClickArg) {
    this.deleteEvaluacijaVisible = true;
    this.clickInfo = clickInfo;
  }

  handleEventDrop(eventDropInfo: EventDropArg){

  }

  handleEventChange(eventChange: EventChangeArg){
    const evaluacijaZnanja = this.evaluacijeZnanja.find(e => e.id = Number(eventChange.event.id));
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

}
