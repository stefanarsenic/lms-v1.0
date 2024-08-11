import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledObavestenjaComponent } from './pregled-obavestenja.component';

describe('PregledObavestenjaComponent', () => {
  let component: PregledObavestenjaComponent;
  let fixture: ComponentFixture<PregledObavestenjaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PregledObavestenjaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PregledObavestenjaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
