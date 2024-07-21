import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OUniverzitetuComponent } from './o-univerzitetu.component';

describe('OUniverzitetuComponent', () => {
  let component: OUniverzitetuComponent;
  let fixture: ComponentFixture<OUniverzitetuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OUniverzitetuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OUniverzitetuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
