import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewrequestsModalComponent } from './viewrequests-modal.component';

describe('ViewrequestsModalComponent', () => {
  let component: ViewrequestsModalComponent;
  let fixture: ComponentFixture<ViewrequestsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewrequestsModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewrequestsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
