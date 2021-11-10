import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmailSubscription, EmailSubscription } from '../email-subscription.model';

import { EmailSubscriptionService } from './email-subscription.service';

describe('EmailSubscription Service', () => {
  let service: EmailSubscriptionService;
  let httpMock: HttpTestingController;
  let elemDefault: IEmailSubscription;
  let expectedResult: IEmailSubscription | IEmailSubscription[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmailSubscriptionService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      email: 'AAAAAAA',
      country: 'AAAAAAA',
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

    it('should create a EmailSubscription', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EmailSubscription()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmailSubscription', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          email: 'BBBBBB',
          country: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmailSubscription', () => {
      const patchObject = Object.assign(
        {
          email: 'BBBBBB',
          country: 'BBBBBB',
        },
        new EmailSubscription()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmailSubscription', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          email: 'BBBBBB',
          country: 'BBBBBB',
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

    it('should delete a EmailSubscription', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEmailSubscriptionToCollectionIfMissing', () => {
      it('should add a EmailSubscription to an empty array', () => {
        const emailSubscription: IEmailSubscription = { id: 123 };
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing([], emailSubscription);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailSubscription);
      });

      it('should not add a EmailSubscription to an array that contains it', () => {
        const emailSubscription: IEmailSubscription = { id: 123 };
        const emailSubscriptionCollection: IEmailSubscription[] = [
          {
            ...emailSubscription,
          },
          { id: 456 },
        ];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, emailSubscription);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmailSubscription to an array that doesn't contain it", () => {
        const emailSubscription: IEmailSubscription = { id: 123 };
        const emailSubscriptionCollection: IEmailSubscription[] = [{ id: 456 }];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, emailSubscription);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailSubscription);
      });

      it('should add only unique EmailSubscription to an array', () => {
        const emailSubscriptionArray: IEmailSubscription[] = [{ id: 123 }, { id: 456 }, { id: 8284 }];
        const emailSubscriptionCollection: IEmailSubscription[] = [{ id: 123 }];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, ...emailSubscriptionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emailSubscription: IEmailSubscription = { id: 123 };
        const emailSubscription2: IEmailSubscription = { id: 456 };
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing([], emailSubscription, emailSubscription2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailSubscription);
        expect(expectedResult).toContain(emailSubscription2);
      });

      it('should accept null and undefined values', () => {
        const emailSubscription: IEmailSubscription = { id: 123 };
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing([], null, emailSubscription, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailSubscription);
      });

      it('should return initial array if no EmailSubscription is added', () => {
        const emailSubscriptionCollection: IEmailSubscription[] = [{ id: 123 }];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, undefined, null);
        expect(expectedResult).toEqual(emailSubscriptionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
