import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDealUserRelation } from '../deal-user-relation.model';
import { DealUserRelationService } from '../service/deal-user-relation.service';
import { DealUserRelationDeleteDialogComponent } from '../delete/deal-user-relation-delete-dialog.component';

@Component({
  selector: 'jhi-deal-user-relation',
  templateUrl: './deal-user-relation.component.html',
})
export class DealUserRelationComponent implements OnInit {
  dealUserRelations?: IDealUserRelation[];
  isLoading = false;

  constructor(protected dealUserRelationService: DealUserRelationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.dealUserRelationService.query().subscribe(
      (res: HttpResponse<IDealUserRelation[]>) => {
        this.isLoading = false;
        this.dealUserRelations = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDealUserRelation): number {
    return item.id!;
  }

  delete(dealUserRelation: IDealUserRelation): void {
    const modalRef = this.modalService.open(DealUserRelationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dealUserRelation = dealUserRelation;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
