import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Addnewjobmodal } from './addnewjobmodal';

describe('Addnewjobmodal', () => {
  let component: Addnewjobmodal;
  let fixture: ComponentFixture<Addnewjobmodal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Addnewjobmodal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Addnewjobmodal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
