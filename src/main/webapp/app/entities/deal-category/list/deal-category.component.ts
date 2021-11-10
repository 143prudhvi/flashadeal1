import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDealCategory } from '../deal-category.model';
import { DealCategoryService } from '../service/deal-category.service';
import { DealCategoryDeleteDialogComponent } from '../delete/deal-category-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-deal-category',
  templateUrl: './deal-category.component.html',
})
export class DealCategoryComponent implements OnInit {
  dealCategories?: IDealCategory[];
  isLoading = false;

  constructor(protected dealCategoryService: DealCategoryService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.dealCategoryService.query().subscribe(
      (res: HttpResponse<IDealCategory[]>) => {
        this.isLoading = false;
        this.dealCategories = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDealCategory): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(dealCategory: IDealCategory): void {
    const modalRef = this.modalService.open(DealCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dealCategory = dealCategory;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
