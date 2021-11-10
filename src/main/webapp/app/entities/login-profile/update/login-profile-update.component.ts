import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILoginProfile, LoginProfile } from '../login-profile.model';
import { LoginProfileService } from '../service/login-profile.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { MemberType } from 'app/entities/enumerations/member-type.model';

@Component({
  selector: 'jhi-login-profile-update',
  templateUrl: './login-profile-update.component.html',
})
export class LoginProfileUpdateComponent implements OnInit {
  isSaving = false;
  memberTypeValues = Object.keys(MemberType);

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    memberType: [],
    phoneNumber: [],
    emailId: [],
    password: [],
    activationCode: [],
    user: [],
  });

  constructor(
    protected loginProfileService: LoginProfileService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loginProfile }) => {
      this.updateForm(loginProfile);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loginProfile = this.createFromForm();
    if (loginProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.loginProfileService.update(loginProfile));
    } else {
      this.subscribeToSaveResponse(this.loginProfileService.create(loginProfile));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoginProfile>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(loginProfile: ILoginProfile): void {
    this.editForm.patchValue({
      id: loginProfile.id,
      memberType: loginProfile.memberType,
      phoneNumber: loginProfile.phoneNumber,
      emailId: loginProfile.emailId,
      password: loginProfile.password,
      activationCode: loginProfile.activationCode,
      user: loginProfile.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, loginProfile.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): ILoginProfile {
    return {
      ...new LoginProfile(),
      id: this.editForm.get(['id'])!.value,
      memberType: this.editForm.get(['memberType'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      emailId: this.editForm.get(['emailId'])!.value,
      password: this.editForm.get(['password'])!.value,
      activationCode: this.editForm.get(['activationCode'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
