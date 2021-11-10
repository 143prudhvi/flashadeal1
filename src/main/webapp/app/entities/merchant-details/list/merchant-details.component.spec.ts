import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MerchantDetailsService } from '../service/merchant-details.service';

import { MerchantDetailsComponent } from './merchant-details.component';

describe('MerchantDetails Management Component', () => {
  let comp: MerchantDetailsComponent;
  let fixture: ComponentFixture<MerchantDetailsComponent>;
  let service: MerchantDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MerchantDetailsComponent],
    })
      .overrideTemplate(MerchantDetailsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MerchantDetailsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MerchantDetailsService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.merchantDetails?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
