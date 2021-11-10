import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDealUserRelation, getDealUserRelationIdentifier } from '../deal-user-relation.model';

export type EntityResponseType = HttpResponse<IDealUserRelation>;
export type EntityArrayResponseType = HttpResponse<IDealUserRelation[]>;

@Injectable({ providedIn: 'root' })
export class DealUserRelationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/deal-user-relations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dealUserRelation: IDealUserRelation): Observable<EntityResponseType> {
    return this.http.post<IDealUserRelation>(this.resourceUrl, dealUserRelation, { observe: 'response' });
  }

  update(dealUserRelation: IDealUserRelation): Observable<EntityResponseType> {
    return this.http.put<IDealUserRelation>(
      `${this.resourceUrl}/${getDealUserRelationIdentifier(dealUserRelation) as number}`,
      dealUserRelation,
      { observe: 'response' }
    );
  }

  partialUpdate(dealUserRelation: IDealUserRelation): Observable<EntityResponseType> {
    return this.http.patch<IDealUserRelation>(
      `${this.resourceUrl}/${getDealUserRelationIdentifier(dealUserRelation) as number}`,
      dealUserRelation,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDealUserRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDealUserRelation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDealUserRelationToCollectionIfMissing(
    dealUserRelationCollection: IDealUserRelation[],
    ...dealUserRelationsToCheck: (IDealUserRelation | null | undefined)[]
  ): IDealUserRelation[] {
    const dealUserRelations: IDealUserRelation[] = dealUserRelationsToCheck.filter(isPresent);
    if (dealUserRelations.length > 0) {
      const dealUserRelationCollectionIdentifiers = dealUserRelationCollection.map(
        dealUserRelationItem => getDealUserRelationIdentifier(dealUserRelationItem)!
      );
      const dealUserRelationsToAdd = dealUserRelations.filter(dealUserRelationItem => {
        const dealUserRelationIdentifier = getDealUserRelationIdentifier(dealUserRelationItem);
        if (dealUserRelationIdentifier == null || dealUserRelationCollectionIdentifiers.includes(dealUserRelationIdentifier)) {
          return false;
        }
        dealUserRelationCollectionIdentifiers.push(dealUserRelationIdentifier);
        return true;
      });
      return [...dealUserRelationsToAdd, ...dealUserRelationCollection];
    }
    return dealUserRelationCollection;
  }
}
