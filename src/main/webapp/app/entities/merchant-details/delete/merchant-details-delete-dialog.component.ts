import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMerchantDetails } from '../merchant-details.model';
import { MerchantDetailsService } from '../service/merchant-details.service';

@Component({
  templateUrl: './merchant-details-delete-dialog.component.html',
})
export class MerchantDetailsDeleteDialogComponent {
  merchantDetails?: IMerchantDetails;

  constructor(protected merchantDetailsService: MerchantDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.merchantDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
