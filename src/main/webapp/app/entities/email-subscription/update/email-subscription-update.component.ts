import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEmailSubscription, EmailSubscription } from '../email-subscription.model';
import { EmailSubscriptionService } from '../service/email-subscription.service';

@Component({
  selector: 'jhi-email-subscription-update',
  templateUrl: './email-subscription-update.component.html',
})
export class EmailSubscriptionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    email: [],
    country: [],
  });

  constructor(
    protected emailSubscriptionService: EmailSubscriptionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailSubscription }) => {
      this.updateForm(emailSubscription);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emailSubscription = this.createFromForm();
    if (emailSubscription.id !== undefined) {
      this.subscribeToSaveResponse(this.emailSubscriptionService.update(emailSubscription));
    } else {
      this.subscribeToSaveResponse(this.emailSubscriptionService.create(emailSubscription));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailSubscription>>): void {
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

  protected updateForm(emailSubscription: IEmailSubscription): void {
    this.editForm.patchValue({
      id: emailSubscription.id,
      email: emailSubscription.email,
      country: emailSubscription.country,
    });
  }

  protected createFromForm(): IEmailSubscription {
    return {
      ...new EmailSubscription(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      country: this.editForm.get(['country'])!.value,
    };
  }
}
