import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDealUserRelation, DealUserRelation } from '../deal-user-relation.model';
import { DealUserRelationService } from '../service/deal-user-relation.service';

@Component({
  selector: 'jhi-deal-user-relation-update',
  templateUrl: './deal-user-relation-update.component.html',
})
export class DealUserRelationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [],
    dealId: [],
  });

  constructor(
    protected dealUserRelationService: DealUserRelationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealUserRelation }) => {
      this.updateForm(dealUserRelation);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dealUserRelation = this.createFromForm();
    if (dealUserRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.dealUserRelationService.update(dealUserRelation));
    } else {
      this.subscribeToSaveResponse(this.dealUserRelationService.create(dealUserRelation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDealUserRelation>>): void {
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

  protected updateForm(dealUserRelation: IDealUserRelation): void {
    this.editForm.patchValue({
      id: dealUserRelation.id,
      userId: dealUserRelation.userId,
      dealId: dealUserRelation.dealId,
    });
  }

  protected createFromForm(): IDealUserRelation {
    return {
      ...new DealUserRelation(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      dealId: this.editForm.get(['dealId'])!.value,
    };
  }
}
