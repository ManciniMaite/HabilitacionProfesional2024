import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmMisTurnosComponent } from './adm-mis-turnos.component';

describe('AdmMisTurnosComponent', () => {
  let component: AdmMisTurnosComponent;
  let fixture: ComponentFixture<AdmMisTurnosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdmMisTurnosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdmMisTurnosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
