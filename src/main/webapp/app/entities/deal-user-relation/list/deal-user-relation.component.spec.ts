import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DealUserRelationService } from '../service/deal-user-relation.service';

import { DealUserRelationComponent } from './deal-user-relation.component';

describe('DealUserRelation Management Component', () => {
  let comp: DealUserRelationComponent;
  let fixture: ComponentFixture<DealUserRelationComponent>;
  let service: DealUserRelationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DealUserRelationComponent],
    })
      .overrideTemplate(DealUserRelationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DealUserRelationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DealUserRelationService);

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
    expect(comp.dealUserRelations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
