import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFlashDeal } from '../flash-deal.model';
import { FlashDealService } from '../service/flash-deal.service';

@Component({
  templateUrl: './flash-deal-delete-dialog.component.html',
})
export class FlashDealDeleteDialogComponent {
  flashDeal?: IFlashDeal;

  constructor(protected flashDealService: FlashDealService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.flashDealService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
