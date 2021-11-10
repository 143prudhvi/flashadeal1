import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DealType } from 'app/entities/enumerations/deal-type.model';
import { DealDisplayPostion } from 'app/entities/enumerations/deal-display-postion.model';
import { ISideDisplayDeal, SideDisplayDeal } from '../side-display-deal.model';

import { SideDisplayDealService } from './side-display-deal.service';

describe('SideDisplayDeal Service', () => {
  let service: SideDisplayDealService;
  let httpMock: HttpTestingController;
  let elemDefault: ISideDisplayDeal;
  let expectedResult: ISideDisplayDeal | ISideDisplayDeal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SideDisplayDealService);
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

    it('should create a SideDisplayDeal', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SideDisplayDeal()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SideDisplayDeal', () => {
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

    it('should partial update a SideDisplayDeal', () => {
      const patchObject = Object.assign(
        {
          type: 'BBBBBB',
          title: 'BBBBBB',
          imageUrl: 'BBBBBB',
          postedBy: 'BBBBBB',
          startDate: 'BBBBBB',
          endDate: 'BBBBBB',
          discount: 'BBBBBB',
        },
        new SideDisplayDeal()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SideDisplayDeal', () => {
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

    it('should delete a SideDisplayDeal', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSideDisplayDealToCollectionIfMissing', () => {
      it('should add a SideDisplayDeal to an empty array', () => {
        const sideDisplayDeal: ISideDisplayDeal = { id: 123 };
        expectedResult = service.addSideDisplayDealToCollectionIfMissing([], sideDisplayDeal);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sideDisplayDeal);
      });

      it('should not add a SideDisplayDeal to an array that contains it', () => {
        const sideDisplayDeal: ISideDisplayDeal = { id: 123 };
        const sideDisplayDealCollection: ISideDisplayDeal[] = [
          {
            ...sideDisplayDeal,
          },
          { id: 456 },
        ];
        expectedResult = service.addSideDisplayDealToCollectionIfMissing(sideDisplayDealCollection, sideDisplayDeal);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SideDisplayDeal to an array that doesn't contain it", () => {
        const sideDisplayDeal: ISideDisplayDeal = { id: 123 };
        const sideDisplayDealCollection: ISideDisplayDeal[] = [{ id: 456 }];
        expectedResult = service.addSideDisplayDealToCollectionIfMissing(sideDisplayDealCollection, sideDisplayDeal);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sideDisplayDeal);
      });

      it('should add only unique SideDisplayDeal to an array', () => {
        const sideDisplayDealArray: ISideDisplayDeal[] = [{ id: 123 }, { id: 456 }, { id: 80914 }];
        const sideDisplayDealCollection: ISideDisplayDeal[] = [{ id: 123 }];
        expectedResult = service.addSideDisplayDealToCollectionIfMissing(sideDisplayDealCollection, ...sideDisplayDealArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sideDisplayDeal: ISideDisplayDeal = { id: 123 };
        const sideDisplayDeal2: ISideDisplayDeal = { id: 456 };
        expectedResult = service.addSideDisplayDealToCollectionIfMissing([], sideDisplayDeal, sideDisplayDeal2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sideDisplayDeal);
        expect(expectedResult).toContain(sideDisplayDeal2);
      });

      it('should accept null and undefined values', () => {
        const sideDisplayDeal: ISideDisplayDeal = { id: 123 };
        expectedResult = service.addSideDisplayDealToCollectionIfMissing([], null, sideDisplayDeal, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sideDisplayDeal);
      });

      it('should return initial array if no SideDisplayDeal is added', () => {
        const sideDisplayDealCollection: ISideDisplayDeal[] = [{ id: 123 }];
        expectedResult = service.addSideDisplayDealToCollectionIfMissing(sideDisplayDealCollection, undefined, null);
        expect(expectedResult).toEqual(sideDisplayDealCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
