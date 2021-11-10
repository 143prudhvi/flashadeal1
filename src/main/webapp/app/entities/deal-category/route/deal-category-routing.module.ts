import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DealCategoryComponent } from '../list/deal-category.component';
import { DealCategoryDetailComponent } from '../detail/deal-category-detail.component';
import { DealCategoryUpdateComponent } from '../update/deal-category-update.component';
import { DealCategoryRoutingResolveService } from './deal-category-routing-resolve.service';

const dealCategoryRoute: Routes = [
  {
    path: '',
    component: DealCategoryComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DealCategoryDetailComponent,
    resolve: {
      dealCategory: DealCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DealCategoryUpdateComponent,
    resolve: {
      dealCategory: DealCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DealCategoryUpdateComponent,
    resolve: {
      dealCategory: DealCategoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dealCategoryRoute)],
  exports: [RouterModule],
})
export class DealCategoryRoutingModule {}
