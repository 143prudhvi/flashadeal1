import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDealCategory, getDealCategoryIdentifier } from '../deal-category.model';

export type EntityResponseType = HttpResponse<IDealCategory>;
export type EntityArrayResponseType = HttpResponse<IDealCategory[]>;

@Injectable({ providedIn: 'root' })
export class DealCategoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/deal-categories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dealCategory: IDealCategory): Observable<EntityResponseType> {
    return this.http.post<IDealCategory>(this.resourceUrl, dealCategory, { observe: 'response' });
  }

  update(dealCategory: IDealCategory): Observable<EntityResponseType> {
    return this.http.put<IDealCategory>(`${this.resourceUrl}/${getDealCategoryIdentifier(dealCategory) as number}`, dealCategory, {
      observe: 'response',
    });
  }

  partialUpdate(dealCategory: IDealCategory): Observable<EntityResponseType> {
    return this.http.patch<IDealCategory>(`${this.resourceUrl}/${getDealCategoryIdentifier(dealCategory) as number}`, dealCategory, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDealCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDealCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDealCategoryToCollectionIfMissing(
    dealCategoryCollection: IDealCategory[],
    ...dealCategoriesToCheck: (IDealCategory | null | undefined)[]
  ): IDealCategory[] {
    const dealCategories: IDealCategory[] = dealCategoriesToCheck.filter(isPresent);
    if (dealCategories.length > 0) {
      const dealCategoryCollectionIdentifiers = dealCategoryCollection.map(
        dealCategoryItem => getDealCategoryIdentifier(dealCategoryItem)!
      );
      const dealCategoriesToAdd = dealCategories.filter(dealCategoryItem => {
        const dealCategoryIdentifier = getDealCategoryIdentifier(dealCategoryItem);
        if (dealCategoryIdentifier == null || dealCategoryCollectionIdentifiers.includes(dealCategoryIdentifier)) {
          return false;
        }
        dealCategoryCollectionIdentifiers.push(dealCategoryIdentifier);
        return true;
      });
      return [...dealCategoriesToAdd, ...dealCategoryCollection];
    }
    return dealCategoryCollection;
  }
}
