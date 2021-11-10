import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MemberType } from 'app/entities/enumerations/member-type.model';
import { ILoginProfile, LoginProfile } from '../login-profile.model';

import { LoginProfileService } from './login-profile.service';

describe('LoginProfile Service', () => {
  let service: LoginProfileService;
  let httpMock: HttpTestingController;
  let elemDefault: ILoginProfile;
  let expectedResult: ILoginProfile | ILoginProfile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoginProfileService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      memberType: MemberType.MERCHANT,
      phoneNumber: 'AAAAAAA',
      emailId: 'AAAAAAA',
      password: 'AAAAAAA',
      activationCode: 'AAAAAAA',
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

    it('should create a LoginProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LoginProfile()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoginProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          memberType: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          emailId: 'BBBBBB',
          password: 'BBBBBB',
          activationCode: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoginProfile', () => {
      const patchObject = Object.assign(
        {
          phoneNumber: 'BBBBBB',
        },
        new LoginProfile()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoginProfile', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          memberType: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          emailId: 'BBBBBB',
          password: 'BBBBBB',
          activationCode: 'BBBBBB',
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

    it('should delete a LoginProfile', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLoginProfileToCollectionIfMissing', () => {
      it('should add a LoginProfile to an empty array', () => {
        const loginProfile: ILoginProfile = { id: 123 };
        expectedResult = service.addLoginProfileToCollectionIfMissing([], loginProfile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loginProfile);
      });

      it('should not add a LoginProfile to an array that contains it', () => {
        const loginProfile: ILoginProfile = { id: 123 };
        const loginProfileCollection: ILoginProfile[] = [
          {
            ...loginProfile,
          },
          { id: 456 },
        ];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, loginProfile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoginProfile to an array that doesn't contain it", () => {
        const loginProfile: ILoginProfile = { id: 123 };
        const loginProfileCollection: ILoginProfile[] = [{ id: 456 }];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, loginProfile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loginProfile);
      });

      it('should add only unique LoginProfile to an array', () => {
        const loginProfileArray: ILoginProfile[] = [{ id: 123 }, { id: 456 }, { id: 42926 }];
        const loginProfileCollection: ILoginProfile[] = [{ id: 123 }];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, ...loginProfileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loginProfile: ILoginProfile = { id: 123 };
        const loginProfile2: ILoginProfile = { id: 456 };
        expectedResult = service.addLoginProfileToCollectionIfMissing([], loginProfile, loginProfile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loginProfile);
        expect(expectedResult).toContain(loginProfile2);
      });

      it('should accept null and undefined values', () => {
        const loginProfile: ILoginProfile = { id: 123 };
        expectedResult = service.addLoginProfileToCollectionIfMissing([], null, loginProfile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loginProfile);
      });

      it('should return initial array if no LoginProfile is added', () => {
        const loginProfileCollection: ILoginProfile[] = [{ id: 123 }];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, undefined, null);
        expect(expectedResult).toEqual(loginProfileCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
