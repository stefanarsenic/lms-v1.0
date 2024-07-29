import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DokumentaComponent } from './dokumenta.component';

describe('DokumentaComponent', () => {
  let component: DokumentaComponent;
  let fixture: ComponentFixture<DokumentaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DokumentaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DokumentaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
