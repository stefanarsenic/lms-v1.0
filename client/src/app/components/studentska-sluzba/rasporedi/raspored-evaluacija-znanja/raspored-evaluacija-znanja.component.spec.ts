import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RasporedEvaluacijaZnanjaComponent } from './raspored-evaluacija-znanja.component';

describe('RasporedEvaluacijeZnanjaComponent', () => {
  let component: RasporedEvaluacijaZnanjaComponent;
  let fixture: ComponentFixture<RasporedEvaluacijaZnanjaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RasporedEvaluacijaZnanjaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RasporedEvaluacijaZnanjaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
