import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScrapperClientComponent } from './scrapper-client.component';

describe('ScrapperClientComponent', () => {
  let component: ScrapperClientComponent;
  let fixture: ComponentFixture<ScrapperClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScrapperClientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScrapperClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
