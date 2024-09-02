import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RasporedIshodaComponent } from './raspored-ishoda.component';

describe('RasporedIshodaComponent', () => {
  let component: RasporedIshodaComponent;
  let fixture: ComponentFixture<RasporedIshodaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RasporedIshodaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RasporedIshodaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
