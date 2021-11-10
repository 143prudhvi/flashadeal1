import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDbCarouselDeal } from '../db-carousel-deal.model';
import { DbCarouselDealService } from '../service/db-carousel-deal.service';

@Component({
  templateUrl: './db-carousel-deal-delete-dialog.component.html',
})
export class DbCarouselDealDeleteDialogComponent {
  dbCarouselDeal?: IDbCarouselDeal;

  constructor(protected dbCarouselDealService: DbCarouselDealService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dbCarouselDealService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
