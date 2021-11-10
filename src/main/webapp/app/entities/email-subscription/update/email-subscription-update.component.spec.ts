jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EmailSubscriptionService } from '../service/email-subscription.service';
import { IEmailSubscription, EmailSubscription } from '../email-subscription.model';

import { EmailSubscriptionUpdateComponent } from './email-subscription-update.component';

describe('EmailSubscription Management Update Component', () => {
  let comp: EmailSubscriptionUpdateComponent;
  let fixture: ComponentFixture<EmailSubscriptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailSubscriptionService: EmailSubscriptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EmailSubscriptionUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(EmailSubscriptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailSubscriptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailSubscriptionService = TestBed.inject(EmailSubscriptionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const emailSubscription: IEmailSubscription = { id: 456 };

      activatedRoute.data = of({ emailSubscription });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(emailSubscription));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EmailSubscription>>();
      const emailSubscription = { id: 123 };
      jest.spyOn(emailSubscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailSubscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailSubscription }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailSubscriptionService.update).toHaveBeenCalledWith(emailSubscription);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EmailSubscription>>();
      const emailSubscription = new EmailSubscription();
      jest.spyOn(emailSubscriptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailSubscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailSubscription }));
      saveSubject.complete();

      // THEN
      expect(emailSubscriptionService.create).toHaveBeenCalledWith(emailSubscription);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EmailSubscription>>();
      const emailSubscription = { id: 123 };
      jest.spyOn(emailSubscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailSubscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailSubscriptionService.update).toHaveBeenCalledWith(emailSubscription);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
