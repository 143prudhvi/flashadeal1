jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFlashDeal, FlashDeal } from '../flash-deal.model';
import { FlashDealService } from '../service/flash-deal.service';

import { FlashDealRoutingResolveService } from './flash-deal-routing-resolve.service';

describe('FlashDeal routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FlashDealRoutingResolveService;
  let service: FlashDealService;
  let resultFlashDeal: IFlashDeal | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(FlashDealRoutingResolveService);
    service = TestBed.inject(FlashDealService);
    resultFlashDeal = undefined;
  });

  describe('resolve', () => {
    it('should return IFlashDeal returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFlashDeal = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFlashDeal).toEqual({ id: 123 });
    });

    it('should return new IFlashDeal if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFlashDeal = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFlashDeal).toEqual(new FlashDeal());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FlashDeal })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFlashDeal = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFlashDeal).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
