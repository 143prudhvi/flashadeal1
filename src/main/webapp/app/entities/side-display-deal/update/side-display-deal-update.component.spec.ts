jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SideDisplayDealService } from '../service/side-display-deal.service';
import { ISideDisplayDeal, SideDisplayDeal } from '../side-display-deal.model';
import { IMerchantDetails } from 'app/entities/merchant-details/merchant-details.model';
import { MerchantDetailsService } from 'app/entities/merchant-details/service/merchant-details.service';
import { IDealCategory } from 'app/entities/deal-category/deal-category.model';
import { DealCategoryService } from 'app/entities/deal-category/service/deal-category.service';

import { SideDisplayDealUpdateComponent } from './side-display-deal-update.component';

describe('SideDisplayDeal Management Update Component', () => {
  let comp: SideDisplayDealUpdateComponent;
  let fixture: ComponentFixture<SideDisplayDealUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sideDisplayDealService: SideDisplayDealService;
  let merchantDetailsService: MerchantDetailsService;
  let dealCategoryService: DealCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SideDisplayDealUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(SideDisplayDealUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SideDisplayDealUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sideDisplayDealService = TestBed.inject(SideDisplayDealService);
    merchantDetailsService = TestBed.inject(MerchantDetailsService);
    dealCategoryService = TestBed.inject(DealCategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MerchantDetails query and add missing value', () => {
      const sideDisplayDeal: ISideDisplayDeal = { id: 456 };
      const merchantDetails: IMerchantDetails = { id: 77686 };
      sideDisplayDeal.merchantDetails = merchantDetails;

      const merchantDetailsCollection: IMerchantDetails[] = [{ id: 51453 }];
      jest.spyOn(merchantDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: merchantDetailsCollection })));
      const additionalMerchantDetails = [merchantDetails];
      const expectedCollection: IMerchantDetails[] = [...additionalMerchantDetails, ...merchantDetailsCollection];
      jest.spyOn(merchantDetailsService, 'addMerchantDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sideDisplayDeal });
      comp.ngOnInit();

      expect(merchantDetailsService.query).toHaveBeenCalled();
      expect(merchantDetailsService.addMerchantDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        merchantDetailsCollection,
        ...additionalMerchantDetails
      );
      expect(comp.merchantDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DealCategory query and add missing value', () => {
      const sideDisplayDeal: ISideDisplayDeal = { id: 456 };
      const dealCategory: IDealCategory = { id: 55581 };
      sideDisplayDeal.dealCategory = dealCategory;

      const dealCategoryCollection: IDealCategory[] = [{ id: 49371 }];
      jest.spyOn(dealCategoryService, 'query').mockReturnValue(of(new HttpResponse({ body: dealCategoryCollection })));
      const additionalDealCategories = [dealCategory];
      const expectedCollection: IDealCategory[] = [...additionalDealCategories, ...dealCategoryCollection];
      jest.spyOn(dealCategoryService, 'addDealCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sideDisplayDeal });
      comp.ngOnInit();

      expect(dealCategoryService.query).toHaveBeenCalled();
      expect(dealCategoryService.addDealCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        dealCategoryCollection,
        ...additionalDealCategories
      );
      expect(comp.dealCategoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sideDisplayDeal: ISideDisplayDeal = { id: 456 };
      const merchantDetails: IMerchantDetails = { id: 87246 };
      sideDisplayDeal.merchantDetails = merchantDetails;
      const dealCategory: IDealCategory = { id: 94159 };
      sideDisplayDeal.dealCategory = dealCategory;

      activatedRoute.data = of({ sideDisplayDeal });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(sideDisplayDeal));
      expect(comp.merchantDetailsSharedCollection).toContain(merchantDetails);
      expect(comp.dealCategoriesSharedCollection).toContain(dealCategory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SideDisplayDeal>>();
      const sideDisplayDeal = { id: 123 };
      jest.spyOn(sideDisplayDealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sideDisplayDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sideDisplayDeal }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(sideDisplayDealService.update).toHaveBeenCalledWith(sideDisplayDeal);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SideDisplayDeal>>();
      const sideDisplayDeal = new SideDisplayDeal();
      jest.spyOn(sideDisplayDealService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sideDisplayDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sideDisplayDeal }));
      saveSubject.complete();

      // THEN
      expect(sideDisplayDealService.create).toHaveBeenCalledWith(sideDisplayDeal);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SideDisplayDeal>>();
      const sideDisplayDeal = { id: 123 };
      jest.spyOn(sideDisplayDealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sideDisplayDeal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sideDisplayDealService.update).toHaveBeenCalledWith(sideDisplayDeal);
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
