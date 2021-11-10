import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDealUserRelation } from '../deal-user-relation.model';

@Component({
  selector: 'jhi-deal-user-relation-detail',
  templateUrl: './deal-user-relation-detail.component.html',
})
export class DealUserRelationDetailComponent implements OnInit {
  dealUserRelation: IDealUserRelation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealUserRelation }) => {
      this.dealUserRelation = dealUserRelation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
