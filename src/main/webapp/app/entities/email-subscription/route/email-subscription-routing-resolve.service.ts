import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmailSubscription, EmailSubscription } from '../email-subscription.model';
import { EmailSubscriptionService } from '../service/email-subscription.service';

@Injectable({ providedIn: 'root' })
export class EmailSubscriptionRoutingResolveService implements Resolve<IEmailSubscription> {
  constructor(protected service: EmailSubscriptionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmailSubscription> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((emailSubscription: HttpResponse<EmailSubscription>) => {
          if (emailSubscription.body) {
            return of(emailSubscription.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmailSubscription());
  }
}
