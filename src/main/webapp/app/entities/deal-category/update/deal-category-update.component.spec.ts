jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DealCategoryService } from '../service/deal-category.service';
import { IDealCategory, DealCategory } from '../deal-category.model';

import { DealCategoryUpdateComponent } from './deal-category-update.component';

describe('DealCategory Management Update Component', () => {
  let comp: DealCategoryUpdateComponent;
  let fixture: ComponentFixture<DealCategoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dealCategoryService: DealCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DealCategoryUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DealCategoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DealCategoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dealCategoryService = TestBed.inject(DealCategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dealCategory: IDealCategory = { id: 456 };

      activatedRoute.data = of({ dealCategory });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dealCategory));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DealCategory>>();
      const dealCategory = { id: 123 };
      jest.spyOn(dealCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dealCategory }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dealCategoryService.update).toHaveBeenCalledWith(dealCategory);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DealCategory>>();
      const dealCategory = new DealCategory();
      jest.spyOn(dealCategoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dealCategory }));
      saveSubject.complete();

      // THEN
      expect(dealCategoryService.create).toHaveBeenCalledWith(dealCategory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DealCategory>>();
      const dealCategory = { id: 123 };
      jest.spyOn(dealCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dealCategoryService.update).toHaveBeenCalledWith(dealCategory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
