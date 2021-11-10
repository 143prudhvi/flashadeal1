import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DealType } from 'app/entities/enumerations/deal-type.model';
import { DealDisplayPostion } from 'app/entities/enumerations/deal-display-postion.model';
import { IDbCarouselDeal, DbCarouselDeal } from '../db-carousel-deal.model';

import { DbCarouselDealService } from './db-carousel-deal.service';

describe('DbCarouselDeal Service', () => {
  let service: DbCarouselDealService;
  let httpMock: HttpTestingController;
  let elemDefault: IDbCarouselDeal;
  let expectedResult: IDbCarouselDeal | IDbCarouselDeal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DbCarouselDealService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      type: DealType.USA_FREEDEAL,
      title: 'AAAAAAA',
      description: 'AAAAAAA',
      imageUrl: 'AAAAAAA',
      dealUrl: 'AAAAAAA',
      postedBy: 'AAAAAAA',
      postedDate: 'AAAAAAA',
      startDate: 'AAAAAAA',
      endDate: 'AAAAAAA',
      originalPrice: 'AAAAAAA',
      currentPrice: 'AAAAAAA',
      discount: 'AAAAAAA',
      active: 'AAAAAAA',
      position: DealDisplayPostion.DASHBOARD_DEAL_TOP_LIST,
      approved: false,
      country: 'AAAAAAA',
      city: 'AAAAAAA',
      pinCode: 'AAAAAAA',
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

    it('should create a DbCarouselDeal', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DbCarouselDeal()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DbCarouselDeal', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          title: 'BBBBBB',
          description: 'BBBBBB',
          imageUrl: 'BBBBBB',
          dealUrl: 'BBBBBB',
          postedBy: 'BBBBBB',
          postedDate: 'BBBBBB',
          startDate: 'BBBBBB',
          endDate: 'BBBBBB',
          originalPrice: 'BBBBBB',
          currentPrice: 'BBBBBB',
          discount: 'BBBBBB',
          active: 'BBBBBB',
          position: 'BBBBBB',
          approved: true,
          country: 'BBBBBB',
          city: 'BBBBBB',
          pinCode: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DbCarouselDeal', () => {
      const patchObject = Object.assign(
        {
          title: 'BBBBBB',
          description: 'BBBBBB',
          imageUrl: 'BBBBBB',
          postedDate: 'BBBBBB',
          endDate: 'BBBBBB',
          originalPrice: 'BBBBBB',
          active: 'BBBBBB',
          city: 'BBBBBB',
        },
        new DbCarouselDeal()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DbCarouselDeal', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          type: 'BBBBBB',
          title: 'BBBBBB',
          description: 'BBBBBB',
          imageUrl: 'BBBBBB',
          dealUrl: 'BBBBBB',
          postedBy: 'BBBBBB',
          postedDate: 'BBBBBB',
          startDate: 'BBBBBB',
          endDate: 'BBBBBB',
          originalPrice: 'BBBBBB',
          currentPrice: 'BBBBBB',
          discount: 'BBBBBB',
          active: 'BBBBBB',
          position: 'BBBBBB',
          approved: true,
          country: 'BBBBBB',
          city: 'BBBBBB',
          pinCode: 'BBBBBB',
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

    it('should delete a DbCarouselDeal', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDbCarouselDealToCollectionIfMissing', () => {
      it('should add a DbCarouselDeal to an empty array', () => {
        const dbCarouselDeal: IDbCarouselDeal = { id: 123 };
        expectedResult = service.addDbCarouselDealToCollectionIfMissing([], dbCarouselDeal);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dbCarouselDeal);
      });

      it('should not add a DbCarouselDeal to an array that contains it', () => {
        const dbCarouselDeal: IDbCarouselDeal = { id: 123 };
        const dbCarouselDealCollection: IDbCarouselDeal[] = [
          {
            ...dbCarouselDeal,
          },
          { id: 456 },
        ];
        expectedResult = service.addDbCarouselDealToCollectionIfMissing(dbCarouselDealCollection, dbCarouselDeal);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DbCarouselDeal to an array that doesn't contain it", () => {
        const dbCarouselDeal: IDbCarouselDeal = { id: 123 };
        const dbCarouselDealCollection: IDbCarouselDeal[] = [{ id: 456 }];
        expectedResult = service.addDbCarouselDealToCollectionIfMissing(dbCarouselDealCollection, dbCarouselDeal);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dbCarouselDeal);
      });

      it('should add only unique DbCarouselDeal to an array', () => {
        const dbCarouselDealArray: IDbCarouselDeal[] = [{ id: 123 }, { id: 456 }, { id: 94473 }];
        const dbCarouselDealCollection: IDbCarouselDeal[] = [{ id: 123 }];
        expectedResult = service.addDbCarouselDealToCollectionIfMissing(dbCarouselDealCollection, ...dbCarouselDealArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dbCarouselDeal: IDbCarouselDeal = { id: 123 };
        const dbCarouselDeal2: IDbCarouselDeal = { id: 456 };
        expectedResult = service.addDbCarouselDealToCollectionIfMissing([], dbCarouselDeal, dbCarouselDeal2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dbCarouselDeal);
        expect(expectedResult).toContain(dbCarouselDeal2);
      });

      it('should accept null and undefined values', () => {
        const dbCarouselDeal: IDbCarouselDeal = { id: 123 };
        expectedResult = service.addDbCarouselDealToCollectionIfMissing([], null, dbCarouselDeal, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dbCarouselDeal);
      });

      it('should return initial array if no DbCarouselDeal is added', () => {
        const dbCarouselDealCollection: IDbCarouselDeal[] = [{ id: 123 }];
        expectedResult = service.addDbCarouselDealToCollectionIfMissing(dbCarouselDealCollection, undefined, null);
        expect(expectedResult).toEqual(dbCarouselDealCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
