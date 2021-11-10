import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISideDisplayDeal } from '../side-display-deal.model';
import { SideDisplayDealService } from '../service/side-display-deal.service';

@Component({
  templateUrl: './side-display-deal-delete-dialog.component.html',
})
export class SideDisplayDealDeleteDialogComponent {
  sideDisplayDeal?: ISideDisplayDeal;

  constructor(protected sideDisplayDealService: SideDisplayDealService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sideDisplayDealService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
