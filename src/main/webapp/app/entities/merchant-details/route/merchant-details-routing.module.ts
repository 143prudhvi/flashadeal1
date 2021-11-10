import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MerchantDetailsComponent } from '../list/merchant-details.component';
import { MerchantDetailsDetailComponent } from '../detail/merchant-details-detail.component';
import { MerchantDetailsUpdateComponent } from '../update/merchant-details-update.component';
import { MerchantDetailsRoutingResolveService } from './merchant-details-routing-resolve.service';

const merchantDetailsRoute: Routes = [
  {
    path: '',
    component: MerchantDetailsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MerchantDetailsDetailComponent,
    resolve: {
      merchantDetails: MerchantDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MerchantDetailsUpdateComponent,
    resolve: {
      merchantDetails: MerchantDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MerchantDetailsUpdateComponent,
    resolve: {
      merchantDetails: MerchantDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(merchantDetailsRoute)],
  exports: [RouterModule],
})
export class MerchantDetailsRoutingModule {}
