import { TestBed } from '@angular/core/testing';

import { IspitService } from './ispit.service';

describe('IspitService', () => {
  let service: IspitService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IspitService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
