import { TestBed } from '@angular/core/testing';

import { PolozeniPredmetService } from './polozeni-predmet.service';

describe('PolozeniPredmetService', () => {
  let service: PolozeniPredmetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PolozeniPredmetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
