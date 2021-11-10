import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBioProfile } from '../bio-profile.model';
import { BioProfileService } from '../service/bio-profile.service';
import { BioProfileDeleteDialogComponent } from '../delete/bio-profile-delete-dialog.component';

@Component({
  selector: 'jhi-bio-profile',
  templateUrl: './bio-profile.component.html',
})
export class BioProfileComponent implements OnInit {
  bioProfiles?: IBioProfile[];
  isLoading = false;

  constructor(protected bioProfileService: BioProfileService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.bioProfileService.query().subscribe(
      (res: HttpResponse<IBioProfile[]>) => {
        this.isLoading = false;
        this.bioProfiles = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBioProfile): number {
    return item.id!;
  }

  delete(bioProfile: IBioProfile): void {
    const modalRef = this.modalService.open(BioProfileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bioProfile = bioProfile;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
