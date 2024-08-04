import { TestBed } from '@angular/core/testing';

import { TestMenuService } from './test-menu.service';

describe('TestMenuService', () => {
  let service: TestMenuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TestMenuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
