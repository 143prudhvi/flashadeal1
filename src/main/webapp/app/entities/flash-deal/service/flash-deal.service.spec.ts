import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DealType } from 'app/entities/enumerations/deal-type.model';
import { DealDisplayPostion } from 'app/entities/enumerations/deal-display-postion.model';
import { IFlashDeal, FlashDeal } from '../flash-deal.model';

import { FlashDealService } from './flash-deal.service';

describe('FlashDeal Service', () => {
  let service: FlashDealService;
  let httpMock: HttpTestingController;
  let elemDefault: IFlashDeal;
  let expectedResult: IFlashDeal | IFlashDeal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FlashDealService);
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

    it('should create a FlashDeal', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new FlashDeal()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FlashDeal', () => {
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

    it('should partial update a FlashDeal', () => {
      const patchObject = Object.assign(
        {
          title: 'BBBBBB',
          description: 'BBBBBB',
          imageUrl: 'BBBBBB',
          dealUrl: 'BBBBBB',
          postedDate: 'BBBBBB',
          startDate: 'BBBBBB',
          endDate: 'BBBBBB',
          approved: true,
        },
        new FlashDeal()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FlashDeal', () => {
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

    it('should delete a FlashDeal', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFlashDealToCollectionIfMissing', () => {
      it('should add a FlashDeal to an empty array', () => {
        const flashDeal: IFlashDeal = { id: 123 };
        expectedResult = service.addFlashDealToCollectionIfMissing([], flashDeal);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(flashDeal);
      });

      it('should not add a FlashDeal to an array that contains it', () => {
        const flashDeal: IFlashDeal = { id: 123 };
        const flashDealCollection: IFlashDeal[] = [
          {
            ...flashDeal,
          },
          { id: 456 },
        ];
        expectedResult = service.addFlashDealToCollectionIfMissing(flashDealCollection, flashDeal);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FlashDeal to an array that doesn't contain it", () => {
        const flashDeal: IFlashDeal = { id: 123 };
        const flashDealCollection: IFlashDeal[] = [{ id: 456 }];
        expectedResult = service.addFlashDealToCollectionIfMissing(flashDealCollection, flashDeal);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(flashDeal);
      });

      it('should add only unique FlashDeal to an array', () => {
        const flashDealArray: IFlashDeal[] = [{ id: 123 }, { id: 456 }, { id: 24476 }];
        const flashDealCollection: IFlashDeal[] = [{ id: 123 }];
        expectedResult = service.addFlashDealToCollectionIfMissing(flashDealCollection, ...flashDealArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const flashDeal: IFlashDeal = { id: 123 };
        const flashDeal2: IFlashDeal = { id: 456 };
        expectedResult = service.addFlashDealToCollectionIfMissing([], flashDeal, flashDeal2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(flashDeal);
        expect(expectedResult).toContain(flashDeal2);
      });

      it('should accept null and undefined values', () => {
        const flashDeal: IFlashDeal = { id: 123 };
        expectedResult = service.addFlashDealToCollectionIfMissing([], null, flashDeal, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(flashDeal);
      });

      it('should return initial array if no FlashDeal is added', () => {
        const flashDealCollection: IFlashDeal[] = [{ id: 123 }];
        expectedResult = service.addFlashDealToCollectionIfMissing(flashDealCollection, undefined, null);
        expect(expectedResult).toEqual(flashDealCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
