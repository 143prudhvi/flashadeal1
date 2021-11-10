import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBioProfile, BioProfile } from '../bio-profile.model';

import { BioProfileService } from './bio-profile.service';

describe('BioProfile Service', () => {
  let service: BioProfileService;
  let httpMock: HttpTestingController;
  let elemDefault: IBioProfile;
  let expectedResult: IBioProfile | IBioProfile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BioProfileService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      dob: 'AAAAAAA',
      gender: 'AAAAAAA',
      imageUrl: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BioProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BioProfile()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BioProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          dob: 'BBBBBB',
          gender: 'BBBBBB',
          imageUrl: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BioProfile', () => {
      const patchObject = Object.assign(
        {
          firstName: 'BBBBBB',
          dob: 'BBBBBB',
        },
        new BioProfile()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BioProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          dob: 'BBBBBB',
          gender: 'BBBBBB',
          imageUrl: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BioProfile', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBioProfileToCollectionIfMissing', () => {
      it('should add a BioProfile to an empty array', () => {
        const bioProfile: IBioProfile = { id: 123 };
        expectedResult = service.addBioProfileToCollectionIfMissing([], bioProfile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bioProfile);
      });

      it('should not add a BioProfile to an array that contains it', () => {
        const bioProfile: IBioProfile = { id: 123 };
        const bioProfileCollection: IBioProfile[] = [
          {
            ...bioProfile,
          },
          { id: 456 },
        ];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, bioProfile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BioProfile to an array that doesn't contain it", () => {
        const bioProfile: IBioProfile = { id: 123 };
        const bioProfileCollection: IBioProfile[] = [{ id: 456 }];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, bioProfile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bioProfile);
      });

      it('should add only unique BioProfile to an array', () => {
        const bioProfileArray: IBioProfile[] = [{ id: 123 }, { id: 456 }, { id: 48223 }];
        const bioProfileCollection: IBioProfile[] = [{ id: 123 }];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, ...bioProfileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bioProfile: IBioProfile = { id: 123 };
        const bioProfile2: IBioProfile = { id: 456 };
        expectedResult = service.addBioProfileToCollectionIfMissing([], bioProfile, bioProfile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bioProfile);
        expect(expectedResult).toContain(bioProfile2);
      });

      it('should accept null and undefined values', () => {
        const bioProfile: IBioProfile = { id: 123 };
        expectedResult = service.addBioProfileToCollectionIfMissing([], null, bioProfile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bioProfile);
      });

      it('should return initial array if no BioProfile is added', () => {
        const bioProfileCollection: IBioProfile[] = [{ id: 123 }];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, undefined, null);
        expect(expectedResult).toEqual(bioProfileCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
