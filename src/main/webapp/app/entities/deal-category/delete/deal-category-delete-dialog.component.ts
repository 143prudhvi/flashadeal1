import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDealCategory } from '../deal-category.model';
import { DealCategoryService } from '../service/deal-category.service';

@Component({
  templateUrl: './deal-category-delete-dialog.component.html',
})
export class DealCategoryDeleteDialogComponent {
  dealCategory?: IDealCategory;

  constructor(protected dealCategoryService: DealCategoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dealCategoryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
