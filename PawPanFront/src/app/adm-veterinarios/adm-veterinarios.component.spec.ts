import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmVeterinariosComponent } from './adm-veterinarios.component';

describe('AdmVeterinariosComponent', () => {
  let component: AdmVeterinariosComponent;
  let fixture: ComponentFixture<AdmVeterinariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdmVeterinariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdmVeterinariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
