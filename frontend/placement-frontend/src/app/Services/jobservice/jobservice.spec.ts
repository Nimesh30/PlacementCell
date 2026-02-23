import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Jobservice } from './jobservice';

describe('Jobservice', () => {
  let component: Jobservice;
  let fixture: ComponentFixture<Jobservice>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Jobservice]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Jobservice);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
