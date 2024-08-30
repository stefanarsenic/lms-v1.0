import { TestBed } from '@angular/core/testing';

import { SemestarService } from './semestar.service';

describe('SemestarService', () => {
  let service: SemestarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SemestarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
