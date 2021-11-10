import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDealCategory } from '../deal-category.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-deal-category-detail',
  templateUrl: './deal-category-detail.component.html',
})
export class DealCategoryDetailComponent implements OnInit {
  dealCategory: IDealCategory | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealCategory }) => {
      this.dealCategory = dealCategory;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
