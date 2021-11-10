import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDealUserRelation, DealUserRelation } from '../deal-user-relation.model';
import { DealUserRelationService } from '../service/deal-user-relation.service';

@Injectable({ providedIn: 'root' })
export class DealUserRelationRoutingResolveService implements Resolve<IDealUserRelation> {
  constructor(protected service: DealUserRelationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDealUserRelation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dealUserRelation: HttpResponse<DealUserRelation>) => {
          if (dealUserRelation.body) {
            return of(dealUserRelation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DealUserRelation());
  }
}
