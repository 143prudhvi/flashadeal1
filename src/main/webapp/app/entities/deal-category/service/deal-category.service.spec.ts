import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDealCategory, DealCategory } from '../deal-category.model';

import { DealCategoryService } from './deal-category.service';

describe('DealCategory Service', () => {
  let service: DealCategoryService;
  let httpMock: HttpTestingController;
  let elemDefault: IDealCategory;
  let expectedResult: IDealCategory | IDealCategory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DealCategoryService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      icon: 'AAAAAAA',
      description: 'AAAAAAA',
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

    it('should create a DealCategory', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DealCategory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DealCategory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          icon: 'BBBBBB',
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DealCategory', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          description: 'BBBBBB',
        },
        new DealCategory()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DealCategory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          icon: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a DealCategory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDealCategoryToCollectionIfMissing', () => {
      it('should add a DealCategory to an empty array', () => {
        const dealCategory: IDealCategory = { id: 123 };
        expectedResult = service.addDealCategoryToCollectionIfMissing([], dealCategory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dealCategory);
      });

      it('should not add a DealCategory to an array that contains it', () => {
        const dealCategory: IDealCategory = { id: 123 };
        const dealCategoryCollection: IDealCategory[] = [
          {
            ...dealCategory,
          },
          { id: 456 },
        ];
        expectedResult = service.addDealCategoryToCollectionIfMissing(dealCategoryCollection, dealCategory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DealCategory to an array that doesn't contain it", () => {
        const dealCategory: IDealCategory = { id: 123 };
        const dealCategoryCollection: IDealCategory[] = [{ id: 456 }];
        expectedResult = service.addDealCategoryToCollectionIfMissing(dealCategoryCollection, dealCategory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dealCategory);
      });

      it('should add only unique DealCategory to an array', () => {
        const dealCategoryArray: IDealCategory[] = [{ id: 123 }, { id: 456 }, { id: 25757 }];
        const dealCategoryCollection: IDealCategory[] = [{ id: 123 }];
        expectedResult = service.addDealCategoryToCollectionIfMissing(dealCategoryCollection, ...dealCategoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dealCategory: IDealCategory = { id: 123 };
        const dealCategory2: IDealCategory = { id: 456 };
        expectedResult = service.addDealCategoryToCollectionIfMissing([], dealCategory, dealCategory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dealCategory);
        expect(expectedResult).toContain(dealCategory2);
      });

      it('should accept null and undefined values', () => {
        const dealCategory: IDealCategory = { id: 123 };
        expectedResult = service.addDealCategoryToCollectionIfMissing([], null, dealCategory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dealCategory);
      });

      it('should return initial array if no DealCategory is added', () => {
        const dealCategoryCollection: IDealCategory[] = [{ id: 123 }];
        expectedResult = service.addDealCategoryToCollectionIfMissing(dealCategoryCollection, undefined, null);
        expect(expectedResult).toEqual(dealCategoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
