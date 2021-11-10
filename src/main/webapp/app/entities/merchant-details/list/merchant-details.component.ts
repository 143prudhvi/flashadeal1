import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMerchantDetails } from '../merchant-details.model';
import { MerchantDetailsService } from '../service/merchant-details.service';
import { MerchantDetailsDeleteDialogComponent } from '../delete/merchant-details-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-merchant-details',
  templateUrl: './merchant-details.component.html',
})
export class MerchantDetailsComponent implements OnInit {
  merchantDetails?: IMerchantDetails[];
  isLoading = false;

  constructor(protected merchantDetailsService: MerchantDetailsService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.merchantDetailsService.query().subscribe(
      (res: HttpResponse<IMerchantDetails[]>) => {
        this.isLoading = false;
        this.merchantDetails = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMerchantDetails): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(merchantDetails: IMerchantDetails): void {
    const modalRef = this.modalService.open(MerchantDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.merchantDetails = merchantDetails;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
