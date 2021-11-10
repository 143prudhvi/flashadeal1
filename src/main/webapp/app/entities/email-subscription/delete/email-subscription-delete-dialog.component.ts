import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmailSubscription } from '../email-subscription.model';
import { EmailSubscriptionService } from '../service/email-subscription.service';

@Component({
  templateUrl: './email-subscription-delete-dialog.component.html',
})
export class EmailSubscriptionDeleteDialogComponent {
  emailSubscription?: IEmailSubscription;

  constructor(protected emailSubscriptionService: EmailSubscriptionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailSubscriptionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
