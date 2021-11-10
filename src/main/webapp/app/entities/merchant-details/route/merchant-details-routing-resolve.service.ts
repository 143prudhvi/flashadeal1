import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMerchantDetails, MerchantDetails } from '../merchant-details.model';
import { MerchantDetailsService } from '../service/merchant-details.service';

@Injectable({ providedIn: 'root' })
export class MerchantDetailsRoutingResolveService implements Resolve<IMerchantDetails> {
  constructor(protected service: MerchantDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMerchantDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((merchantDetails: HttpResponse<MerchantDetails>) => {
          if (merchantDetails.body) {
            return of(merchantDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MerchantDetails());
  }
}
