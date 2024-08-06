import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpisakStudenataComponent } from './spisak-studenata.component';

describe('SpisakStudenataComponent', () => {
  let component: SpisakStudenataComponent;
  let fixture: ComponentFixture<SpisakStudenataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpisakStudenataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpisakStudenataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
