import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMerchantDetails, getMerchantDetailsIdentifier } from '../merchant-details.model';

export type EntityResponseType = HttpResponse<IMerchantDetails>;
export type EntityArrayResponseType = HttpResponse<IMerchantDetails[]>;

@Injectable({ providedIn: 'root' })
export class MerchantDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/merchant-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(merchantDetails: IMerchantDetails): Observable<EntityResponseType> {
    return this.http.post<IMerchantDetails>(this.resourceUrl, merchantDetails, { observe: 'response' });
  }

  update(merchantDetails: IMerchantDetails): Observable<EntityResponseType> {
    return this.http.put<IMerchantDetails>(
      `${this.resourceUrl}/${getMerchantDetailsIdentifier(merchantDetails) as number}`,
      merchantDetails,
      { observe: 'response' }
    );
  }

  partialUpdate(merchantDetails: IMerchantDetails): Observable<EntityResponseType> {
    return this.http.patch<IMerchantDetails>(
      `${this.resourceUrl}/${getMerchantDetailsIdentifier(merchantDetails) as number}`,
      merchantDetails,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMerchantDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMerchantDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMerchantDetailsToCollectionIfMissing(
    merchantDetailsCollection: IMerchantDetails[],
    ...merchantDetailsToCheck: (IMerchantDetails | null | undefined)[]
  ): IMerchantDetails[] {
    const merchantDetails: IMerchantDetails[] = merchantDetailsToCheck.filter(isPresent);
    if (merchantDetails.length > 0) {
      const merchantDetailsCollectionIdentifiers = merchantDetailsCollection.map(
        merchantDetailsItem => getMerchantDetailsIdentifier(merchantDetailsItem)!
      );
      const merchantDetailsToAdd = merchantDetails.filter(merchantDetailsItem => {
        const merchantDetailsIdentifier = getMerchantDetailsIdentifier(merchantDetailsItem);
        if (merchantDetailsIdentifier == null || merchantDetailsCollectionIdentifiers.includes(merchantDetailsIdentifier)) {
          return false;
        }
        merchantDetailsCollectionIdentifiers.push(merchantDetailsIdentifier);
        return true;
      });
      return [...merchantDetailsToAdd, ...merchantDetailsCollection];
    }
    return merchantDetailsCollection;
  }
}
