import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFlashDeal, FlashDeal } from '../flash-deal.model';
import { FlashDealService } from '../service/flash-deal.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IMerchantDetails } from 'app/entities/merchant-details/merchant-details.model';
import { MerchantDetailsService } from 'app/entities/merchant-details/service/merchant-details.service';
import { IDealCategory } from 'app/entities/deal-category/deal-category.model';
import { DealCategoryService } from 'app/entities/deal-category/service/deal-category.service';
import { DealType } from 'app/entities/enumerations/deal-type.model';
import { DealDisplayPostion } from 'app/entities/enumerations/deal-display-postion.model';

@Component({
  selector: 'jhi-flash-deal-update',
  templateUrl: './flash-deal-update.component.html',
})
export class FlashDealUpdateComponent implements OnInit {
  isSaving = false;
  dealTypeValues = Object.keys(DealType);
  dealDisplayPostionValues = Object.keys(DealDisplayPostion);

  merchantDetailsSharedCollection: IMerchantDetails[] = [];
  dealCategoriesSharedCollection: IDealCategory[] = [];

  editForm = this.fb.group({
    id: [],
    type: [],
    title: [],
    description: [],
    imageUrl: [],
    dealUrl: [],
    postedBy: [],
    postedDate: [],
    startDate: [],
    endDate: [],
    originalPrice: [],
    currentPrice: [],
    discount: [],
    active: [],
    position: [],
    approved: [],
    country: [],
    city: [],
    pinCode: [],
    merchantDetails: [],
    dealCategory: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected flashDealService: FlashDealService,
    protected merchantDetailsService: MerchantDetailsService,
    protected dealCategoryService: DealCategoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flashDeal }) => {
      this.updateForm(flashDeal);

      this.loadRelationshipsOptions();
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
    const flashDeal = this.createFromForm();
    if (flashDeal.id !== undefined) {
      this.subscribeToSaveResponse(this.flashDealService.update(flashDeal));
    } else {
      this.subscribeToSaveResponse(this.flashDealService.create(flashDeal));
    }
  }

  trackMerchantDetailsById(index: number, item: IMerchantDetails): number {
    return item.id!;
  }

  trackDealCategoryById(index: number, item: IDealCategory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlashDeal>>): void {
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

  protected updateForm(flashDeal: IFlashDeal): void {
    this.editForm.patchValue({
      id: flashDeal.id,
      type: flashDeal.type,
      title: flashDeal.title,
      description: flashDeal.description,
      imageUrl: flashDeal.imageUrl,
      dealUrl: flashDeal.dealUrl,
      postedBy: flashDeal.postedBy,
      postedDate: flashDeal.postedDate,
      startDate: flashDeal.startDate,
      endDate: flashDeal.endDate,
      originalPrice: flashDeal.originalPrice,
      currentPrice: flashDeal.currentPrice,
      discount: flashDeal.discount,
      active: flashDeal.active,
      position: flashDeal.position,
      approved: flashDeal.approved,
      country: flashDeal.country,
      city: flashDeal.city,
      pinCode: flashDeal.pinCode,
      merchantDetails: flashDeal.merchantDetails,
      dealCategory: flashDeal.dealCategory,
    });

    this.merchantDetailsSharedCollection = this.merchantDetailsService.addMerchantDetailsToCollectionIfMissing(
      this.merchantDetailsSharedCollection,
      flashDeal.merchantDetails
    );
    this.dealCategoriesSharedCollection = this.dealCategoryService.addDealCategoryToCollectionIfMissing(
      this.dealCategoriesSharedCollection,
      flashDeal.dealCategory
    );
  }

  protected loadRelationshipsOptions(): void {
    this.merchantDetailsService
      .query()
      .pipe(map((res: HttpResponse<IMerchantDetails[]>) => res.body ?? []))
      .pipe(
        map((merchantDetails: IMerchantDetails[]) =>
          this.merchantDetailsService.addMerchantDetailsToCollectionIfMissing(merchantDetails, this.editForm.get('merchantDetails')!.value)
        )
      )
      .subscribe((merchantDetails: IMerchantDetails[]) => (this.merchantDetailsSharedCollection = merchantDetails));

    this.dealCategoryService
      .query()
      .pipe(map((res: HttpResponse<IDealCategory[]>) => res.body ?? []))
      .pipe(
        map((dealCategories: IDealCategory[]) =>
          this.dealCategoryService.addDealCategoryToCollectionIfMissing(dealCategories, this.editForm.get('dealCategory')!.value)
        )
      )
      .subscribe((dealCategories: IDealCategory[]) => (this.dealCategoriesSharedCollection = dealCategories));
  }

  protected createFromForm(): IFlashDeal {
    return {
      ...new FlashDeal(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      dealUrl: this.editForm.get(['dealUrl'])!.value,
      postedBy: this.editForm.get(['postedBy'])!.value,
      postedDate: this.editForm.get(['postedDate'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      originalPrice: this.editForm.get(['originalPrice'])!.value,
      currentPrice: this.editForm.get(['currentPrice'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      active: this.editForm.get(['active'])!.value,
      position: this.editForm.get(['position'])!.value,
      approved: this.editForm.get(['approved'])!.value,
      country: this.editForm.get(['country'])!.value,
      city: this.editForm.get(['city'])!.value,
      pinCode: this.editForm.get(['pinCode'])!.value,
      merchantDetails: this.editForm.get(['merchantDetails'])!.value,
      dealCategory: this.editForm.get(['dealCategory'])!.value,
    };
  }
}
