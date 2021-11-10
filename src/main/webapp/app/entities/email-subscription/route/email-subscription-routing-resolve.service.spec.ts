jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEmailSubscription, EmailSubscription } from '../email-subscription.model';
import { EmailSubscriptionService } from '../service/email-subscription.service';

import { EmailSubscriptionRoutingResolveService } from './email-subscription-routing-resolve.service';

describe('EmailSubscription routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EmailSubscriptionRoutingResolveService;
  let service: EmailSubscriptionService;
  let resultEmailSubscription: IEmailSubscription | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(EmailSubscriptionRoutingResolveService);
    service = TestBed.inject(EmailSubscriptionService);
    resultEmailSubscription = undefined;
  });

  describe('resolve', () => {
    it('should return IEmailSubscription returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmailSubscription = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmailSubscription).toEqual({ id: 123 });
    });

    it('should return new IEmailSubscription if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmailSubscription = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEmailSubscription).toEqual(new EmailSubscription());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EmailSubscription })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmailSubscription = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmailSubscription).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
