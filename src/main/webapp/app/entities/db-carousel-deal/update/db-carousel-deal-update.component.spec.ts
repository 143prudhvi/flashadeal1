jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DbCarouselDealService } from '../service/db-carousel-deal.service';
import { IDbCarouselDeal, DbCarouselDeal } from '../db-carousel-deal.model';
import { IMerchantDetails } from 'app/entities/merchant-details/merchant-details.model';
import { MerchantDetailsService } from 'app/entities/merchant-details/service/merchant-details.service';
import { IDealCategory } from 'app/entities/deal-category/deal-category.model';
import { DealCategoryService } from 'app/entities/deal-category/service/deal-category.service';

import { DbCarouselDealUpdateComponent } from './db-carousel-deal-update.component';

describe('DbCarouselDeal Management Update Component', () => {
  let comp: DbCarouselDealUpdateComponent;
  let fixture: ComponentFixture<DbCarouselDealUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dbCarouselDealService: DbCarouselDealService;
  let merchantDetailsService: MerchantDetailsService;
  let dealCategoryService: DealCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DbCarouselDealUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(DbCarouselDealUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DbCarouselDealUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dbCarouselDealService = TestBed.inject(DbCarouselDealService);
    merchantDetailsService = TestBed.inject(MerchantDetailsService);
    dealCategoryService = TestBed.inject(DealCategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MerchantDetails query and add missing value', () => {
      const dbCarouselDeal: IDbCarouselDeal = { id: 456 };
      const merchantDetails: IMerchantDetails = { id: 35489 };
      dbCarouselDeal.merchantDetails = merchantDetails;

      const merchantDetailsCollection: IMerchantDetails[] = [{ id: 22772 }];
      jest.spyOn(merchantDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: merchantDetailsCollection })));
      const additionalMerchantDetails = [merchantDetails];
      const expectedCollection: IMerchantDetails[] = [...additionalMerchantDetails, ...merchantDetailsCollection];
      jest.spyOn(merchantDetailsService, 'addMerchantDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dbCarouselDeal });
      comp.ngOnInit();

      expect(merchantDetailsService.query).toHaveBeenCalled();
      expect(merchantDetailsService.addMerchantDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        merchantDetailsCollection,
        ...additionalMerchantDetails
      );
      expect(comp.merchantDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DealCategory query and add missing value', () => {
      const dbCarouselDeal: IDbCarouselDeal = { id: 456 };
      const dealCategory: IDealCategory = { id: 99660 };
      dbCarouselDeal.dealCategory = dealCategory;

      const dealCategoryCollection: IDealCategory[] = [{ id: 42805 }];
      jest.spyOn(dealCategoryService, 'query').mockReturnValue(of(new HttpResponse({ body: dealCategoryCollection })));
      const additionalDealCategories = [dealCategory];
      const expectedCollection: IDealCategory[] = [...additionalDealCategories, ...dealCategoryCollection];
      jest.spyOn(dealCategoryService, 'addDealCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dbCarouselDeal });
      comp.ngOnInit();

      expect(dealCategoryService.query).toHaveBeenCalled();
      expect(dealCategoryService.addDealCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        dealCategoryCollection,
        ...additionalDealCategories
      );
      expect(comp.dealCategoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dbCarouselDeal: IDbCarouselDeal = { id: 456 };
      const merchantDetails: IMerchantDetails = { id: 31605 };
      dbCarouselDeal.merchantDetails = merchantDetails;
      const dealCategory: IDealCategory = { id: 20022 };
      dbCarouselDeal.dealCategory = dealCategory;

      activatedRoute.data = of({ dbCarouselDeal });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(dbCarouselDeal));
      expect(comp.merchantDetailsSharedCollection).toContain(merchantDetails);
      expect(comp.dealCategoriesSharedCollection).toContain(dealCategory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DbCarouselDeal>>();
      const dbCarouselDeal = { id: 123 };
      jest.spyOn(dbCarouselDealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dbCarouselDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dbCarouselDeal }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(dbCarouselDealService.update).toHaveBeenCalledWith(dbCarouselDeal);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DbCarouselDeal>>();
      const dbCarouselDeal = new DbCarouselDeal();
      jest.spyOn(dbCarouselDealService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dbCarouselDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dbCarouselDeal }));
      saveSubject.complete();

      // THEN
      expect(dbCarouselDealService.create).toHaveBeenCalledWith(dbCarouselDeal);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DbCarouselDeal>>();
      const dbCarouselDeal = { id: 123 };
      jest.spyOn(dbCarouselDealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dbCarouselDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dbCarouselDealService.update).toHaveBeenCalledWith(dbCarouselDeal);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMerchantDetailsById', () => {
      it('Should return tracked MerchantDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackMerchantDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDealCategoryById', () => {
      it('Should return tracked DealCategory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDealCategoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
