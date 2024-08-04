import { TestBed } from '@angular/core/testing';

import { LayoutTestService } from './layout-test.service';

describe('LayoutTestService', () => {
  let service: LayoutTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LayoutTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
