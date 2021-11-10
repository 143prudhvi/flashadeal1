jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BioProfileService } from '../service/bio-profile.service';
import { IBioProfile, BioProfile } from '../bio-profile.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { BioProfileUpdateComponent } from './bio-profile-update.component';

describe('BioProfile Management Update Component', () => {
  let comp: BioProfileUpdateComponent;
  let fixture: ComponentFixture<BioProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bioProfileService: BioProfileService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BioProfileUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(BioProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BioProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bioProfileService = TestBed.inject(BioProfileService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const bioProfile: IBioProfile = { id: 456 };
      const user: IUser = { id: 81450 };
      bioProfile.user = user;

      const userCollection: IUser[] = [{ id: 73048 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bioProfile: IBioProfile = { id: 456 };
      const user: IUser = { id: 91488 };
      bioProfile.user = user;

      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bioProfile));
      expect(comp.usersSharedCollection).toContain(user);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BioProfile>>();
      const bioProfile = { id: 123 };
      jest.spyOn(bioProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bioProfile }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bioProfileService.update).toHaveBeenCalledWith(bioProfile);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BioProfile>>();
      const bioProfile = new BioProfile();
      jest.spyOn(bioProfileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bioProfile }));
      saveSubject.complete();

      // THEN
      expect(bioProfileService.create).toHaveBeenCalledWith(bioProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BioProfile>>();
      const bioProfile = { id: 123 };
      jest.spyOn(bioProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bioProfileService.update).toHaveBeenCalledWith(bioProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
