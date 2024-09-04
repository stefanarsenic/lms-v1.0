import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledPredmetaNastavnikComponent } from './pregled-predmeta-nastavnik.component';

describe('PregledStudenataComponent', () => {
  let component: PregledPredmetaNastavnikComponent;
  let fixture: ComponentFixture<PregledPredmetaNastavnikComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PregledPredmetaNastavnikComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PregledPredmetaNastavnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
