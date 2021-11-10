import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFlashDeal, getFlashDealIdentifier } from '../flash-deal.model';

export type EntityResponseType = HttpResponse<IFlashDeal>;
export type EntityArrayResponseType = HttpResponse<IFlashDeal[]>;

@Injectable({ providedIn: 'root' })
export class FlashDealService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/flash-deals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(flashDeal: IFlashDeal): Observable<EntityResponseType> {
    return this.http.post<IFlashDeal>(this.resourceUrl, flashDeal, { observe: 'response' });
  }

  update(flashDeal: IFlashDeal): Observable<EntityResponseType> {
    return this.http.put<IFlashDeal>(`${this.resourceUrl}/${getFlashDealIdentifier(flashDeal) as number}`, flashDeal, {
      observe: 'response',
    });
  }

  partialUpdate(flashDeal: IFlashDeal): Observable<EntityResponseType> {
    return this.http.patch<IFlashDeal>(`${this.resourceUrl}/${getFlashDealIdentifier(flashDeal) as number}`, flashDeal, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFlashDeal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFlashDeal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFlashDealToCollectionIfMissing(
    flashDealCollection: IFlashDeal[],
    ...flashDealsToCheck: (IFlashDeal | null | undefined)[]
  ): IFlashDeal[] {
    const flashDeals: IFlashDeal[] = flashDealsToCheck.filter(isPresent);
    if (flashDeals.length > 0) {
      const flashDealCollectionIdentifiers = flashDealCollection.map(flashDealItem => getFlashDealIdentifier(flashDealItem)!);
      const flashDealsToAdd = flashDeals.filter(flashDealItem => {
        const flashDealIdentifier = getFlashDealIdentifier(flashDealItem);
        if (flashDealIdentifier == null || flashDealCollectionIdentifiers.includes(flashDealIdentifier)) {
          return false;
        }
        flashDealCollectionIdentifiers.push(flashDealIdentifier);
        return true;
      });
      return [...flashDealsToAdd, ...flashDealCollection];
    }
    return flashDealCollection;
  }
}
