jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMerchantDetails, MerchantDetails } from '../merchant-details.model';
import { MerchantDetailsService } from '../service/merchant-details.service';

import { MerchantDetailsRoutingResolveService } from './merchant-details-routing-resolve.service';

describe('MerchantDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MerchantDetailsRoutingResolveService;
  let service: MerchantDetailsService;
  let resultMerchantDetails: IMerchantDetails | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(MerchantDetailsRoutingResolveService);
    service = TestBed.inject(MerchantDetailsService);
    resultMerchantDetails = undefined;
  });

  describe('resolve', () => {
    it('should return IMerchantDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMerchantDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMerchantDetails).toEqual({ id: 123 });
    });

    it('should return new IMerchantDetails if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMerchantDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMerchantDetails).toEqual(new MerchantDetails());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MerchantDetails })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMerchantDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMerchantDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
