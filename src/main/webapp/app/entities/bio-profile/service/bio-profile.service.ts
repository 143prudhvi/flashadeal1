import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBioProfile, getBioProfileIdentifier } from '../bio-profile.model';

export type EntityResponseType = HttpResponse<IBioProfile>;
export type EntityArrayResponseType = HttpResponse<IBioProfile[]>;

@Injectable({ providedIn: 'root' })
export class BioProfileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bio-profiles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bioProfile: IBioProfile): Observable<EntityResponseType> {
    return this.http.post<IBioProfile>(this.resourceUrl, bioProfile, { observe: 'response' });
  }

  update(bioProfile: IBioProfile): Observable<EntityResponseType> {
    return this.http.put<IBioProfile>(`${this.resourceUrl}/${getBioProfileIdentifier(bioProfile) as number}`, bioProfile, {
      observe: 'response',
    });
  }

  partialUpdate(bioProfile: IBioProfile): Observable<EntityResponseType> {
    return this.http.patch<IBioProfile>(`${this.resourceUrl}/${getBioProfileIdentifier(bioProfile) as number}`, bioProfile, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBioProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBioProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBioProfileToCollectionIfMissing(
    bioProfileCollection: IBioProfile[],
    ...bioProfilesToCheck: (IBioProfile | null | undefined)[]
  ): IBioProfile[] {
    const bioProfiles: IBioProfile[] = bioProfilesToCheck.filter(isPresent);
    if (bioProfiles.length > 0) {
      const bioProfileCollectionIdentifiers = bioProfileCollection.map(bioProfileItem => getBioProfileIdentifier(bioProfileItem)!);
      const bioProfilesToAdd = bioProfiles.filter(bioProfileItem => {
        const bioProfileIdentifier = getBioProfileIdentifier(bioProfileItem);
        if (bioProfileIdentifier == null || bioProfileCollectionIdentifiers.includes(bioProfileIdentifier)) {
          return false;
        }
        bioProfileCollectionIdentifiers.push(bioProfileIdentifier);
        return true;
      });
      return [...bioProfilesToAdd, ...bioProfileCollection];
    }
    return bioProfileCollection;
  }
}
