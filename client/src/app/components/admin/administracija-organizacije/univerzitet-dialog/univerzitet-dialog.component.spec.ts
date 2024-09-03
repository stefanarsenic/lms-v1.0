import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UniverzitetDialogComponent } from './univerzitet-dialog.component';

describe('UniverzitetDialogComponent', () => {
  let component: UniverzitetDialogComponent;
  let fixture: ComponentFixture<UniverzitetDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UniverzitetDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UniverzitetDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
