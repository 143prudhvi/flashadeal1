import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDealUserRelation } from '../deal-user-relation.model';
import { DealUserRelationService } from '../service/deal-user-relation.service';

@Component({
  templateUrl: './deal-user-relation-delete-dialog.component.html',
})
export class DealUserRelationDeleteDialogComponent {
  dealUserRelation?: IDealUserRelation;

  constructor(protected dealUserRelationService: DealUserRelationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dealUserRelationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
