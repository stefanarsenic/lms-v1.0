import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudijskiProgramCrudComponent } from './studijski-program-crud.component';

describe('StudijskiProgramCrudComponent', () => {
  let component: StudijskiProgramCrudComponent;
  let fixture: ComponentFixture<StudijskiProgramCrudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudijskiProgramCrudComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudijskiProgramCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
