import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NastavaComponent } from './nastava.component';

describe('NastavaComponent', () => {
  let component: NastavaComponent;
  let fixture: ComponentFixture<NastavaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NastavaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NastavaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
