import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledPredmetaComponent } from './pregled-predmeta.component';

describe('PregledPredmetaComponent', () => {
  let component: PregledPredmetaComponent;
  let fixture: ComponentFixture<PregledPredmetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PregledPredmetaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PregledPredmetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
