jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FlashDealService } from '../service/flash-deal.service';
import { IFlashDeal, FlashDeal } from '../flash-deal.model';
import { IMerchantDetails } from 'app/entities/merchant-details/merchant-details.model';
import { MerchantDetailsService } from 'app/entities/merchant-details/service/merchant-details.service';
import { IDealCategory } from 'app/entities/deal-category/deal-category.model';
import { DealCategoryService } from 'app/entities/deal-category/service/deal-category.service';

import { FlashDealUpdateComponent } from './flash-deal-update.component';

describe('FlashDeal Management Update Component', () => {
  let comp: FlashDealUpdateComponent;
  let fixture: ComponentFixture<FlashDealUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let flashDealService: FlashDealService;
  let merchantDetailsService: MerchantDetailsService;
  let dealCategoryService: DealCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FlashDealUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(FlashDealUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FlashDealUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    flashDealService = TestBed.inject(FlashDealService);
    merchantDetailsService = TestBed.inject(MerchantDetailsService);
    dealCategoryService = TestBed.inject(DealCategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MerchantDetails query and add missing value', () => {
      const flashDeal: IFlashDeal = { id: 456 };
      const merchantDetails: IMerchantDetails = { id: 14025 };
      flashDeal.merchantDetails = merchantDetails;

      const merchantDetailsCollection: IMerchantDetails[] = [{ id: 88922 }];
      jest.spyOn(merchantDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: merchantDetailsCollection })));
      const additionalMerchantDetails = [merchantDetails];
      const expectedCollection: IMerchantDetails[] = [...additionalMerchantDetails, ...merchantDetailsCollection];
      jest.spyOn(merchantDetailsService, 'addMerchantDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ flashDeal });
      comp.ngOnInit();

      expect(merchantDetailsService.query).toHaveBeenCalled();
      expect(merchantDetailsService.addMerchantDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        merchantDetailsCollection,
        ...additionalMerchantDetails
      );
      expect(comp.merchantDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DealCategory query and add missing value', () => {
      const flashDeal: IFlashDeal = { id: 456 };
      const dealCategory: IDealCategory = { id: 1204 };
      flashDeal.dealCategory = dealCategory;

      const dealCategoryCollection: IDealCategory[] = [{ id: 15449 }];
      jest.spyOn(dealCategoryService, 'query').mockReturnValue(of(new HttpResponse({ body: dealCategoryCollection })));
      const additionalDealCategories = [dealCategory];
      const expectedCollection: IDealCategory[] = [...additionalDealCategories, ...dealCategoryCollection];
      jest.spyOn(dealCategoryService, 'addDealCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ flashDeal });
      comp.ngOnInit();

      expect(dealCategoryService.query).toHaveBeenCalled();
      expect(dealCategoryService.addDealCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        dealCategoryCollection,
        ...additionalDealCategories
      );
      expect(comp.dealCategoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const flashDeal: IFlashDeal = { id: 456 };
      const merchantDetails: IMerchantDetails = { id: 68859 };
      flashDeal.merchantDetails = merchantDetails;
      const dealCategory: IDealCategory = { id: 83795 };
      flashDeal.dealCategory = dealCategory;

      activatedRoute.data = of({ flashDeal });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(flashDeal));
      expect(comp.merchantDetailsSharedCollection).toContain(merchantDetails);
      expect(comp.dealCategoriesSharedCollection).toContain(dealCategory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FlashDeal>>();
      const flashDeal = { id: 123 };
      jest.spyOn(flashDealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ flashDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: flashDeal }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(flashDealService.update).toHaveBeenCalledWith(flashDeal);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FlashDeal>>();
      const flashDeal = new FlashDeal();
      jest.spyOn(flashDealService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ flashDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: flashDeal }));
      saveSubject.complete();

      // THEN
      expect(flashDealService.create).toHaveBeenCalledWith(flashDeal);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FlashDeal>>();
      const flashDeal = { id: 123 };
      jest.spyOn(flashDealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ flashDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(flashDealService.update).toHaveBeenCalledWith(flashDeal);
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
