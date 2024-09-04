import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DinamickiPrikazPredmetaComponent } from './dinamicki-prikaz-predmeta.component';

describe('DinamickiPrikazPredmetaComponent', () => {
  let component: DinamickiPrikazPredmetaComponent;
  let fixture: ComponentFixture<DinamickiPrikazPredmetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DinamickiPrikazPredmetaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DinamickiPrikazPredmetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
