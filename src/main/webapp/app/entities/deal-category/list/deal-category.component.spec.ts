import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DealCategoryService } from '../service/deal-category.service';

import { DealCategoryComponent } from './deal-category.component';

describe('DealCategory Management Component', () => {
  let comp: DealCategoryComponent;
  let fixture: ComponentFixture<DealCategoryComponent>;
  let service: DealCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DealCategoryComponent],
    })
      .overrideTemplate(DealCategoryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DealCategoryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DealCategoryService);

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
    expect(comp.dealCategories?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
