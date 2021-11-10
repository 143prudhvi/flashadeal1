import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFlashDeal, FlashDeal } from '../flash-deal.model';
import { FlashDealService } from '../service/flash-deal.service';

@Injectable({ providedIn: 'root' })
export class FlashDealRoutingResolveService implements Resolve<IFlashDeal> {
  constructor(protected service: FlashDealService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFlashDeal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((flashDeal: HttpResponse<FlashDeal>) => {
          if (flashDeal.body) {
            return of(flashDeal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FlashDeal());
  }
}
