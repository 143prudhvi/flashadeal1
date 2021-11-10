jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MerchantDetailsService } from '../service/merchant-details.service';
import { IMerchantDetails, MerchantDetails } from '../merchant-details.model';

import { MerchantDetailsUpdateComponent } from './merchant-details-update.component';

describe('MerchantDetails Management Update Component', () => {
  let comp: MerchantDetailsUpdateComponent;
  let fixture: ComponentFixture<MerchantDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let merchantDetailsService: MerchantDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MerchantDetailsUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MerchantDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MerchantDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    merchantDetailsService = TestBed.inject(MerchantDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const merchantDetails: IMerchantDetails = { id: 456 };

      activatedRoute.data = of({ merchantDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(merchantDetails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MerchantDetails>>();
      const merchantDetails = { id: 123 };
      jest.spyOn(merchantDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ merchantDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: merchantDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(merchantDetailsService.update).toHaveBeenCalledWith(merchantDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MerchantDetails>>();
      const merchantDetails = new MerchantDetails();
      jest.spyOn(merchantDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ merchantDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: merchantDetails }));
      saveSubject.complete();

      // THEN
      expect(merchantDetailsService.create).toHaveBeenCalledWith(merchantDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MerchantDetails>>();
      const merchantDetails = { id: 123 };
      jest.spyOn(merchantDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ merchantDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(merchantDetailsService.update).toHaveBeenCalledWith(merchantDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
