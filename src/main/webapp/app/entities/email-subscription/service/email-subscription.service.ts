import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmailSubscription, getEmailSubscriptionIdentifier } from '../email-subscription.model';

export type EntityResponseType = HttpResponse<IEmailSubscription>;
export type EntityArrayResponseType = HttpResponse<IEmailSubscription[]>;

@Injectable({ providedIn: 'root' })
export class EmailSubscriptionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/email-subscriptions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(emailSubscription: IEmailSubscription): Observable<EntityResponseType> {
    return this.http.post<IEmailSubscription>(this.resourceUrl, emailSubscription, { observe: 'response' });
  }

  update(emailSubscription: IEmailSubscription): Observable<EntityResponseType> {
    return this.http.put<IEmailSubscription>(
      `${this.resourceUrl}/${getEmailSubscriptionIdentifier(emailSubscription) as number}`,
      emailSubscription,
      { observe: 'response' }
    );
  }

  partialUpdate(emailSubscription: IEmailSubscription): Observable<EntityResponseType> {
    return this.http.patch<IEmailSubscription>(
      `${this.resourceUrl}/${getEmailSubscriptionIdentifier(emailSubscription) as number}`,
      emailSubscription,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmailSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmailSubscription[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmailSubscriptionToCollectionIfMissing(
    emailSubscriptionCollection: IEmailSubscription[],
    ...emailSubscriptionsToCheck: (IEmailSubscription | null | undefined)[]
  ): IEmailSubscription[] {
    const emailSubscriptions: IEmailSubscription[] = emailSubscriptionsToCheck.filter(isPresent);
    if (emailSubscriptions.length > 0) {
      const emailSubscriptionCollectionIdentifiers = emailSubscriptionCollection.map(
        emailSubscriptionItem => getEmailSubscriptionIdentifier(emailSubscriptionItem)!
      );
      const emailSubscriptionsToAdd = emailSubscriptions.filter(emailSubscriptionItem => {
        const emailSubscriptionIdentifier = getEmailSubscriptionIdentifier(emailSubscriptionItem);
        if (emailSubscriptionIdentifier == null || emailSubscriptionCollectionIdentifiers.includes(emailSubscriptionIdentifier)) {
          return false;
        }
        emailSubscriptionCollectionIdentifiers.push(emailSubscriptionIdentifier);
        return true;
      });
      return [...emailSubscriptionsToAdd, ...emailSubscriptionCollection];
    }
    return emailSubscriptionCollection;
  }
}
