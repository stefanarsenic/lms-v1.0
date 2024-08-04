import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpisakPredmetaComponent } from './spisak-predmeta.component';

describe('SpisakPredmetaComponent', () => {
  let component: SpisakPredmetaComponent;
  let fixture: ComponentFixture<SpisakPredmetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpisakPredmetaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpisakPredmetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
