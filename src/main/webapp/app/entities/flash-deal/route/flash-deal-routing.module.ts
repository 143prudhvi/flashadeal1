import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FlashDealComponent } from '../list/flash-deal.component';
import { FlashDealDetailComponent } from '../detail/flash-deal-detail.component';
import { FlashDealUpdateComponent } from '../update/flash-deal-update.component';
import { FlashDealRoutingResolveService } from './flash-deal-routing-resolve.service';

const flashDealRoute: Routes = [
  {
    path: '',
    component: FlashDealComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FlashDealDetailComponent,
    resolve: {
      flashDeal: FlashDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FlashDealUpdateComponent,
    resolve: {
      flashDeal: FlashDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FlashDealUpdateComponent,
    resolve: {
      flashDeal: FlashDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(flashDealRoute)],
  exports: [RouterModule],
})
export class FlashDealRoutingModule {}
