import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDbCarouselDeal, DbCarouselDeal } from '../db-carousel-deal.model';
import { DbCarouselDealService } from '../service/db-carousel-deal.service';

@Injectable({ providedIn: 'root' })
export class DbCarouselDealRoutingResolveService implements Resolve<IDbCarouselDeal> {
  constructor(protected service: DbCarouselDealService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDbCarouselDeal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dbCarouselDeal: HttpResponse<DbCarouselDeal>) => {
          if (dbCarouselDeal.body) {
            return of(dbCarouselDeal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DbCarouselDeal());
  }
}
