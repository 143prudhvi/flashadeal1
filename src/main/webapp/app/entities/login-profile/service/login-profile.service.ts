import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoginProfile, getLoginProfileIdentifier } from '../login-profile.model';

export type EntityResponseType = HttpResponse<ILoginProfile>;
export type EntityArrayResponseType = HttpResponse<ILoginProfile[]>;

@Injectable({ providedIn: 'root' })
export class LoginProfileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/login-profiles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loginProfile: ILoginProfile): Observable<EntityResponseType> {
    return this.http.post<ILoginProfile>(this.resourceUrl, loginProfile, { observe: 'response' });
  }

  update(loginProfile: ILoginProfile): Observable<EntityResponseType> {
    return this.http.put<ILoginProfile>(`${this.resourceUrl}/${getLoginProfileIdentifier(loginProfile) as number}`, loginProfile, {
      observe: 'response',
    });
  }

  partialUpdate(loginProfile: ILoginProfile): Observable<EntityResponseType> {
    return this.http.patch<ILoginProfile>(`${this.resourceUrl}/${getLoginProfileIdentifier(loginProfile) as number}`, loginProfile, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILoginProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILoginProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLoginProfileToCollectionIfMissing(
    loginProfileCollection: ILoginProfile[],
    ...loginProfilesToCheck: (ILoginProfile | null | undefined)[]
  ): ILoginProfile[] {
    const loginProfiles: ILoginProfile[] = loginProfilesToCheck.filter(isPresent);
    if (loginProfiles.length > 0) {
      const loginProfileCollectionIdentifiers = loginProfileCollection.map(
        loginProfileItem => getLoginProfileIdentifier(loginProfileItem)!
      );
      const loginProfilesToAdd = loginProfiles.filter(loginProfileItem => {
        const loginProfileIdentifier = getLoginProfileIdentifier(loginProfileItem);
        if (loginProfileIdentifier == null || loginProfileCollectionIdentifiers.includes(loginProfileIdentifier)) {
          return false;
        }
        loginProfileCollectionIdentifiers.push(loginProfileIdentifier);
        return true;
      });
      return [...loginProfilesToAdd, ...loginProfileCollection];
    }
    return loginProfileCollection;
  }
}
