import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDealUserRelation, DealUserRelation } from '../deal-user-relation.model';

import { DealUserRelationService } from './deal-user-relation.service';

describe('DealUserRelation Service', () => {
  let service: DealUserRelationService;
  let httpMock: HttpTestingController;
  let elemDefault: IDealUserRelation;
  let expectedResult: IDealUserRelation | IDealUserRelation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DealUserRelationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      userId: 'AAAAAAA',
      dealId: 'AAAAAAA',
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

    it('should create a DealUserRelation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DealUserRelation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DealUserRelation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          userId: 'BBBBBB',
          dealId: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DealUserRelation', () => {
      const patchObject = Object.assign(
        {
          dealId: 'BBBBBB',
        },
        new DealUserRelation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DealUserRelation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          userId: 'BBBBBB',
          dealId: 'BBBBBB',
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

    it('should delete a DealUserRelation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDealUserRelationToCollectionIfMissing', () => {
      it('should add a DealUserRelation to an empty array', () => {
        const dealUserRelation: IDealUserRelation = { id: 123 };
        expectedResult = service.addDealUserRelationToCollectionIfMissing([], dealUserRelation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dealUserRelation);
      });

      it('should not add a DealUserRelation to an array that contains it', () => {
        const dealUserRelation: IDealUserRelation = { id: 123 };
        const dealUserRelationCollection: IDealUserRelation[] = [
          {
            ...dealUserRelation,
          },
          { id: 456 },
        ];
        expectedResult = service.addDealUserRelationToCollectionIfMissing(dealUserRelationCollection, dealUserRelation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DealUserRelation to an array that doesn't contain it", () => {
        const dealUserRelation: IDealUserRelation = { id: 123 };
        const dealUserRelationCollection: IDealUserRelation[] = [{ id: 456 }];
        expectedResult = service.addDealUserRelationToCollectionIfMissing(dealUserRelationCollection, dealUserRelation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dealUserRelation);
      });

      it('should add only unique DealUserRelation to an array', () => {
        const dealUserRelationArray: IDealUserRelation[] = [{ id: 123 }, { id: 456 }, { id: 39379 }];
        const dealUserRelationCollection: IDealUserRelation[] = [{ id: 123 }];
        expectedResult = service.addDealUserRelationToCollectionIfMissing(dealUserRelationCollection, ...dealUserRelationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dealUserRelation: IDealUserRelation = { id: 123 };
        const dealUserRelation2: IDealUserRelation = { id: 456 };
        expectedResult = service.addDealUserRelationToCollectionIfMissing([], dealUserRelation, dealUserRelation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dealUserRelation);
        expect(expectedResult).toContain(dealUserRelation2);
      });

      it('should accept null and undefined values', () => {
        const dealUserRelation: IDealUserRelation = { id: 123 };
        expectedResult = service.addDealUserRelationToCollectionIfMissing([], null, dealUserRelation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dealUserRelation);
      });

      it('should return initial array if no DealUserRelation is added', () => {
        const dealUserRelationCollection: IDealUserRelation[] = [{ id: 123 }];
        expectedResult = service.addDealUserRelationToCollectionIfMissing(dealUserRelationCollection, undefined, null);
        expect(expectedResult).toEqual(dealUserRelationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
