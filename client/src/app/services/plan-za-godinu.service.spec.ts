import { TestBed } from '@angular/core/testing';

import { PlanZaGodinuService } from './plan-za-godinu.service';

describe('PlanZaGodinuService', () => {
  let service: PlanZaGodinuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanZaGodinuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
