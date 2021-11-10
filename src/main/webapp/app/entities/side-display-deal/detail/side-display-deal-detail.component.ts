import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISideDisplayDeal } from '../side-display-deal.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-side-display-deal-detail',
  templateUrl: './side-display-deal-detail.component.html',
})
export class SideDisplayDealDetailComponent implements OnInit {
  sideDisplayDeal: ISideDisplayDeal | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sideDisplayDeal }) => {
      this.sideDisplayDeal = sideDisplayDeal;
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
