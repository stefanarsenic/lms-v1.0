import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PretragaStudenataComponent } from './pretraga-studenata.component';

describe('PretragaStudenataComponent', () => {
  let component: PretragaStudenataComponent;
  let fixture: ComponentFixture<PretragaStudenataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PretragaStudenataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PretragaStudenataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
