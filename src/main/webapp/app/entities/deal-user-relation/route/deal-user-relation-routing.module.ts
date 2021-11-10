import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DealUserRelationComponent } from '../list/deal-user-relation.component';
import { DealUserRelationDetailComponent } from '../detail/deal-user-relation-detail.component';
import { DealUserRelationUpdateComponent } from '../update/deal-user-relation-update.component';
import { DealUserRelationRoutingResolveService } from './deal-user-relation-routing-resolve.service';

const dealUserRelationRoute: Routes = [
  {
    path: '',
    component: DealUserRelationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DealUserRelationDetailComponent,
    resolve: {
      dealUserRelation: DealUserRelationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DealUserRelationUpdateComponent,
    resolve: {
      dealUserRelation: DealUserRelationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DealUserRelationUpdateComponent,
    resolve: {
      dealUserRelation: DealUserRelationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dealUserRelationRoute)],
  exports: [RouterModule],
})
export class DealUserRelationRoutingModule {}
