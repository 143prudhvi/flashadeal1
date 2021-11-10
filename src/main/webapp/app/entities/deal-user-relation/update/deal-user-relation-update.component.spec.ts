jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DealUserRelationService } from '../service/deal-user-relation.service';
import { IDealUserRelation, DealUserRelation } from '../deal-user-relation.model';

import { DealUserRelationUpdateComponent } from './deal-user-relation-update.component';

describe('DealUserRelation Management Update Component', () => {
  let comp: DealUserRelationUpdateComponent;
  let fixture: ComponentFixture<DealUserRelationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dealUserRelationService: DealUserRelationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DealUserRelationUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DealUserRelationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DealUserRelationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dealUserRelationService = TestBed.inject(DealUserRelationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dealUserRelation: IDealUserRelation = { id: 456 };

      activatedRoute.data = of({ dealUserRelation });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dealUserRelation));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DealUserRelation>>();
      const dealUserRelation = { id: 123 };
      jest.spyOn(dealUserRelationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealUserRelation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dealUserRelation }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dealUserRelationService.update).toHaveBeenCalledWith(dealUserRelation);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DealUserRelation>>();
      const dealUserRelation = new DealUserRelation();
      jest.spyOn(dealUserRelationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealUserRelation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dealUserRelation }));
      saveSubject.complete();

      // THEN
      expect(dealUserRelationService.create).toHaveBeenCalledWith(dealUserRelation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DealUserRelation>>();
      const dealUserRelation = { id: 123 };
      jest.spyOn(dealUserRelationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealUserRelation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dealUserRelationService.update).toHaveBeenCalledWith(dealUserRelation);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
