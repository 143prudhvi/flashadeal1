import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SideDisplayDealComponent } from '../list/side-display-deal.component';
import { SideDisplayDealDetailComponent } from '../detail/side-display-deal-detail.component';
import { SideDisplayDealUpdateComponent } from '../update/side-display-deal-update.component';
import { SideDisplayDealRoutingResolveService } from './side-display-deal-routing-resolve.service';

const sideDisplayDealRoute: Routes = [
  {
    path: '',
    component: SideDisplayDealComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SideDisplayDealDetailComponent,
    resolve: {
      sideDisplayDeal: SideDisplayDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SideDisplayDealUpdateComponent,
    resolve: {
      sideDisplayDeal: SideDisplayDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SideDisplayDealUpdateComponent,
    resolve: {
      sideDisplayDeal: SideDisplayDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sideDisplayDealRoute)],
  exports: [RouterModule],
})
export class SideDisplayDealRoutingModule {}
