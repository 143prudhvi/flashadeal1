import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DealUserRelationDetailComponent } from './deal-user-relation-detail.component';

describe('DealUserRelation Management Detail Component', () => {
  let comp: DealUserRelationDetailComponent;
  let fixture: ComponentFixture<DealUserRelationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DealUserRelationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dealUserRelation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DealUserRelationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DealUserRelationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dealUserRelation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dealUserRelation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
