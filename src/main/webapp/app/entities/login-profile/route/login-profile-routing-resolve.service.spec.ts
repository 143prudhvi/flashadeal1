jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILoginProfile, LoginProfile } from '../login-profile.model';
import { LoginProfileService } from '../service/login-profile.service';

import { LoginProfileRoutingResolveService } from './login-profile-routing-resolve.service';

describe('LoginProfile routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LoginProfileRoutingResolveService;
  let service: LoginProfileService;
  let resultLoginProfile: ILoginProfile | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(LoginProfileRoutingResolveService);
    service = TestBed.inject(LoginProfileService);
    resultLoginProfile = undefined;
  });

  describe('resolve', () => {
    it('should return ILoginProfile returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLoginProfile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLoginProfile).toEqual({ id: 123 });
    });

    it('should return new ILoginProfile if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLoginProfile = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLoginProfile).toEqual(new LoginProfile());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LoginProfile })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLoginProfile = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLoginProfile).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
