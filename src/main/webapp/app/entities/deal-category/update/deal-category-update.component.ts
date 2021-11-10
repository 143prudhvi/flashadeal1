import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDealCategory, DealCategory } from '../deal-category.model';
import { DealCategoryService } from '../service/deal-category.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-deal-category-update',
  templateUrl: './deal-category-update.component.html',
})
export class DealCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    icon: [],
    description: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected dealCategoryService: DealCategoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealCategory }) => {
      this.updateForm(dealCategory);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('flashadeal1App.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dealCategory = this.createFromForm();
    if (dealCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.dealCategoryService.update(dealCategory));
    } else {
      this.subscribeToSaveResponse(this.dealCategoryService.create(dealCategory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDealCategory>>): void {
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

  protected updateForm(dealCategory: IDealCategory): void {
    this.editForm.patchValue({
      id: dealCategory.id,
      name: dealCategory.name,
      icon: dealCategory.icon,
      description: dealCategory.description,
    });
  }

  protected createFromForm(): IDealCategory {
    return {
      ...new DealCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      icon: this.editForm.get(['icon'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }
}
