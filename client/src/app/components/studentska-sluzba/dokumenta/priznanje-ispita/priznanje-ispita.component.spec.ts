import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriznanjeIspitaComponent } from './priznanje-ispita.component';

describe('PriznanjeIspitaComponent', () => {
  let component: PriznanjeIspitaComponent;
  let fixture: ComponentFixture<PriznanjeIspitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PriznanjeIspitaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriznanjeIspitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
