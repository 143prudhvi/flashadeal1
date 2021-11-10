import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmailSubscription } from '../email-subscription.model';
import { EmailSubscriptionService } from '../service/email-subscription.service';
import { EmailSubscriptionDeleteDialogComponent } from '../delete/email-subscription-delete-dialog.component';

@Component({
  selector: 'jhi-email-subscription',
  templateUrl: './email-subscription.component.html',
})
export class EmailSubscriptionComponent implements OnInit {
  emailSubscriptions?: IEmailSubscription[];
  isLoading = false;

  constructor(protected emailSubscriptionService: EmailSubscriptionService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.emailSubscriptionService.query().subscribe(
      (res: HttpResponse<IEmailSubscription[]>) => {
        this.isLoading = false;
        this.emailSubscriptions = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEmailSubscription): number {
    return item.id!;
  }

  delete(emailSubscription: IEmailSubscription): void {
    const modalRef = this.modalService.open(EmailSubscriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emailSubscription = emailSubscription;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
