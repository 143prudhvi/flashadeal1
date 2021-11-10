import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMerchantDetails, MerchantDetails } from '../merchant-details.model';
import { MerchantDetailsService } from '../service/merchant-details.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { MerchantLocationType } from 'app/entities/enumerations/merchant-location-type.model';

@Component({
  selector: 'jhi-merchant-details-update',
  templateUrl: './merchant-details-update.component.html',
})
export class MerchantDetailsUpdateComponent implements OnInit {
  isSaving = false;
  merchantLocationTypeValues = Object.keys(MerchantLocationType);

  editForm = this.fb.group({
    id: [],
    name: [],
    country: [],
    city: [],
    storeIcon: [],
    type: [],
    location: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected merchantDetailsService: MerchantDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ merchantDetails }) => {
      this.updateForm(merchantDetails);
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
    const merchantDetails = this.createFromForm();
    if (merchantDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.merchantDetailsService.update(merchantDetails));
    } else {
      this.subscribeToSaveResponse(this.merchantDetailsService.create(merchantDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMerchantDetails>>): void {
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

  protected updateForm(merchantDetails: IMerchantDetails): void {
    this.editForm.patchValue({
      id: merchantDetails.id,
      name: merchantDetails.name,
      country: merchantDetails.country,
      city: merchantDetails.city,
      storeIcon: merchantDetails.storeIcon,
      type: merchantDetails.type,
      location: merchantDetails.location,
    });
  }

  protected createFromForm(): IMerchantDetails {
    return {
      ...new MerchantDetails(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      country: this.editForm.get(['country'])!.value,
      city: this.editForm.get(['city'])!.value,
      storeIcon: this.editForm.get(['storeIcon'])!.value,
      type: this.editForm.get(['type'])!.value,
      location: this.editForm.get(['location'])!.value,
    };
  }
}
