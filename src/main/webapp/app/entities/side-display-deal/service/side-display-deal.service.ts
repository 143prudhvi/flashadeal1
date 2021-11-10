import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISideDisplayDeal, getSideDisplayDealIdentifier } from '../side-display-deal.model';

export type EntityResponseType = HttpResponse<ISideDisplayDeal>;
export type EntityArrayResponseType = HttpResponse<ISideDisplayDeal[]>;

@Injectable({ providedIn: 'root' })
export class SideDisplayDealService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/side-display-deals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sideDisplayDeal: ISideDisplayDeal): Observable<EntityResponseType> {
    return this.http.post<ISideDisplayDeal>(this.resourceUrl, sideDisplayDeal, { observe: 'response' });
  }

  update(sideDisplayDeal: ISideDisplayDeal): Observable<EntityResponseType> {
    return this.http.put<ISideDisplayDeal>(
      `${this.resourceUrl}/${getSideDisplayDealIdentifier(sideDisplayDeal) as number}`,
      sideDisplayDeal,
      { observe: 'response' }
    );
  }

  partialUpdate(sideDisplayDeal: ISideDisplayDeal): Observable<EntityResponseType> {
    return this.http.patch<ISideDisplayDeal>(
      `${this.resourceUrl}/${getSideDisplayDealIdentifier(sideDisplayDeal) as number}`,
      sideDisplayDeal,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISideDisplayDeal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISideDisplayDeal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSideDisplayDealToCollectionIfMissing(
    sideDisplayDealCollection: ISideDisplayDeal[],
    ...sideDisplayDealsToCheck: (ISideDisplayDeal | null | undefined)[]
  ): ISideDisplayDeal[] {
    const sideDisplayDeals: ISideDisplayDeal[] = sideDisplayDealsToCheck.filter(isPresent);
    if (sideDisplayDeals.length > 0) {
      const sideDisplayDealCollectionIdentifiers = sideDisplayDealCollection.map(
        sideDisplayDealItem => getSideDisplayDealIdentifier(sideDisplayDealItem)!
      );
      const sideDisplayDealsToAdd = sideDisplayDeals.filter(sideDisplayDealItem => {
        const sideDisplayDealIdentifier = getSideDisplayDealIdentifier(sideDisplayDealItem);
        if (sideDisplayDealIdentifier == null || sideDisplayDealCollectionIdentifiers.includes(sideDisplayDealIdentifier)) {
          return false;
        }
        sideDisplayDealCollectionIdentifiers.push(sideDisplayDealIdentifier);
        return true;
      });
      return [...sideDisplayDealsToAdd, ...sideDisplayDealCollection];
    }
    return sideDisplayDealCollection;
  }
}
