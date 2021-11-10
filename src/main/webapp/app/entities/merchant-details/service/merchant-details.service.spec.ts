import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MerchantLocationType } from 'app/entities/enumerations/merchant-location-type.model';
import { IMerchantDetails, MerchantDetails } from '../merchant-details.model';

import { MerchantDetailsService } from './merchant-details.service';

describe('MerchantDetails Service', () => {
  let service: MerchantDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IMerchantDetails;
  let expectedResult: IMerchantDetails | IMerchantDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MerchantDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      country: 'AAAAAAA',
      city: 'AAAAAAA',
      storeIcon: 'AAAAAAA',
      type: 'AAAAAAA',
      location: MerchantLocationType.INSTORE,
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

    it('should create a MerchantDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new MerchantDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MerchantDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          country: 'BBBBBB',
          city: 'BBBBBB',
          storeIcon: 'BBBBBB',
          type: 'BBBBBB',
          location: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MerchantDetails', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          country: 'BBBBBB',
          storeIcon: 'BBBBBB',
          location: 'BBBBBB',
        },
        new MerchantDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MerchantDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          country: 'BBBBBB',
          city: 'BBBBBB',
          storeIcon: 'BBBBBB',
          type: 'BBBBBB',
          location: 'BBBBBB',
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

    it('should delete a MerchantDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMerchantDetailsToCollectionIfMissing', () => {
      it('should add a MerchantDetails to an empty array', () => {
        const merchantDetails: IMerchantDetails = { id: 123 };
        expectedResult = service.addMerchantDetailsToCollectionIfMissing([], merchantDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(merchantDetails);
      });

      it('should not add a MerchantDetails to an array that contains it', () => {
        const merchantDetails: IMerchantDetails = { id: 123 };
        const merchantDetailsCollection: IMerchantDetails[] = [
          {
            ...merchantDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addMerchantDetailsToCollectionIfMissing(merchantDetailsCollection, merchantDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MerchantDetails to an array that doesn't contain it", () => {
        const merchantDetails: IMerchantDetails = { id: 123 };
        const merchantDetailsCollection: IMerchantDetails[] = [{ id: 456 }];
        expectedResult = service.addMerchantDetailsToCollectionIfMissing(merchantDetailsCollection, merchantDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(merchantDetails);
      });

      it('should add only unique MerchantDetails to an array', () => {
        const merchantDetailsArray: IMerchantDetails[] = [{ id: 123 }, { id: 456 }, { id: 17310 }];
        const merchantDetailsCollection: IMerchantDetails[] = [{ id: 123 }];
        expectedResult = service.addMerchantDetailsToCollectionIfMissing(merchantDetailsCollection, ...merchantDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const merchantDetails: IMerchantDetails = { id: 123 };
        const merchantDetails2: IMerchantDetails = { id: 456 };
        expectedResult = service.addMerchantDetailsToCollectionIfMissing([], merchantDetails, merchantDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(merchantDetails);
        expect(expectedResult).toContain(merchantDetails2);
      });

      it('should accept null and undefined values', () => {
        const merchantDetails: IMerchantDetails = { id: 123 };
        expectedResult = service.addMerchantDetailsToCollectionIfMissing([], null, merchantDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(merchantDetails);
      });

      it('should return initial array if no MerchantDetails is added', () => {
        const merchantDetailsCollection: IMerchantDetails[] = [{ id: 123 }];
        expectedResult = service.addMerchantDetailsToCollectionIfMissing(merchantDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(merchantDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
