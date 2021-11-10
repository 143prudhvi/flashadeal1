import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDealCategory, DealCategory } from '../deal-category.model';
import { DealCategoryService } from '../service/deal-category.service';

@Injectable({ providedIn: 'root' })
export class DealCategoryRoutingResolveService implements Resolve<IDealCategory> {
  constructor(protected service: DealCategoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDealCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dealCategory: HttpResponse<DealCategory>) => {
          if (dealCategory.body) {
            return of(dealCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DealCategory());
  }
}
