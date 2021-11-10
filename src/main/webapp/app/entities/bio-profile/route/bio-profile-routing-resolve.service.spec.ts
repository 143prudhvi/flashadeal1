jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBioProfile, BioProfile } from '../bio-profile.model';
import { BioProfileService } from '../service/bio-profile.service';

import { BioProfileRoutingResolveService } from './bio-profile-routing-resolve.service';

describe('BioProfile routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BioProfileRoutingResolveService;
  let service: BioProfileService;
  let resultBioProfile: IBioProfile | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(BioProfileRoutingResolveService);
    service = TestBed.inject(BioProfileService);
    resultBioProfile = undefined;
  });

  describe('resolve', () => {
    it('should return IBioProfile returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBioProfile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBioProfile).toEqual({ id: 123 });
    });

    it('should return new IBioProfile if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBioProfile = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBioProfile).toEqual(new BioProfile());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BioProfile })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBioProfile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBioProfile).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
