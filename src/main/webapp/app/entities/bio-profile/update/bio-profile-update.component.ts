import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBioProfile, BioProfile } from '../bio-profile.model';
import { BioProfileService } from '../service/bio-profile.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-bio-profile-update',
  templateUrl: './bio-profile-update.component.html',
})
export class BioProfileUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    dob: [],
    gender: [],
    imageUrl: [],
    user: [],
  });

  constructor(
    protected bioProfileService: BioProfileService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bioProfile }) => {
      this.updateForm(bioProfile);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bioProfile = this.createFromForm();
    if (bioProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.bioProfileService.update(bioProfile));
    } else {
      this.subscribeToSaveResponse(this.bioProfileService.create(bioProfile));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBioProfile>>): void {
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

  protected updateForm(bioProfile: IBioProfile): void {
    this.editForm.patchValue({
      id: bioProfile.id,
      firstName: bioProfile.firstName,
      lastName: bioProfile.lastName,
      dob: bioProfile.dob,
      gender: bioProfile.gender,
      imageUrl: bioProfile.imageUrl,
      user: bioProfile.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, bioProfile.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IBioProfile {
    return {
      ...new BioProfile(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
