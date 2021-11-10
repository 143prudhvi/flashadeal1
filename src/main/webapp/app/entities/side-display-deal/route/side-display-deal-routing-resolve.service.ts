import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISideDisplayDeal, SideDisplayDeal } from '../side-display-deal.model';
import { SideDisplayDealService } from '../service/side-display-deal.service';

@Injectable({ providedIn: 'root' })
export class SideDisplayDealRoutingResolveService implements Resolve<ISideDisplayDeal> {
  constructor(protected service: SideDisplayDealService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISideDisplayDeal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sideDisplayDeal: HttpResponse<SideDisplayDeal>) => {
          if (sideDisplayDeal.body) {
            return of(sideDisplayDeal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SideDisplayDeal());
  }
}
