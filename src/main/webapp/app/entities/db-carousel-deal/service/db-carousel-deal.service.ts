import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDbCarouselDeal, getDbCarouselDealIdentifier } from '../db-carousel-deal.model';

export type EntityResponseType = HttpResponse<IDbCarouselDeal>;
export type EntityArrayResponseType = HttpResponse<IDbCarouselDeal[]>;

@Injectable({ providedIn: 'root' })
export class DbCarouselDealService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/db-carousel-deals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dbCarouselDeal: IDbCarouselDeal): Observable<EntityResponseType> {
    return this.http.post<IDbCarouselDeal>(this.resourceUrl, dbCarouselDeal, { observe: 'response' });
  }

  update(dbCarouselDeal: IDbCarouselDeal): Observable<EntityResponseType> {
    return this.http.put<IDbCarouselDeal>(`${this.resourceUrl}/${getDbCarouselDealIdentifier(dbCarouselDeal) as number}`, dbCarouselDeal, {
      observe: 'response',
    });
  }

  partialUpdate(dbCarouselDeal: IDbCarouselDeal): Observable<EntityResponseType> {
    return this.http.patch<IDbCarouselDeal>(
      `${this.resourceUrl}/${getDbCarouselDealIdentifier(dbCarouselDeal) as number}`,
      dbCarouselDeal,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDbCarouselDeal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDbCarouselDeal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDbCarouselDealToCollectionIfMissing(
    dbCarouselDealCollection: IDbCarouselDeal[],
    ...dbCarouselDealsToCheck: (IDbCarouselDeal | null | undefined)[]
  ): IDbCarouselDeal[] {
    const dbCarouselDeals: IDbCarouselDeal[] = dbCarouselDealsToCheck.filter(isPresent);
    if (dbCarouselDeals.length > 0) {
      const dbCarouselDealCollectionIdentifiers = dbCarouselDealCollection.map(
        dbCarouselDealItem => getDbCarouselDealIdentifier(dbCarouselDealItem)!
      );
      const dbCarouselDealsToAdd = dbCarouselDeals.filter(dbCarouselDealItem => {
        const dbCarouselDealIdentifier = getDbCarouselDealIdentifier(dbCarouselDealItem);
        if (dbCarouselDealIdentifier == null || dbCarouselDealCollectionIdentifiers.includes(dbCarouselDealIdentifier)) {
          return false;
        }
        dbCarouselDealCollectionIdentifiers.push(dbCarouselDealIdentifier);
        return true;
      });
      return [...dbCarouselDealsToAdd, ...dbCarouselDealCollection];
    }
    return dbCarouselDealCollection;
  }
}
