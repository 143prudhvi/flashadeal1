import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DbCarouselDealComponent } from '../list/db-carousel-deal.component';
import { DbCarouselDealDetailComponent } from '../detail/db-carousel-deal-detail.component';
import { DbCarouselDealUpdateComponent } from '../update/db-carousel-deal-update.component';
import { DbCarouselDealRoutingResolveService } from './db-carousel-deal-routing-resolve.service';

const dbCarouselDealRoute: Routes = [
  {
    path: '',
    component: DbCarouselDealComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DbCarouselDealDetailComponent,
    resolve: {
      dbCarouselDeal: DbCarouselDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DbCarouselDealUpdateComponent,
    resolve: {
      dbCarouselDeal: DbCarouselDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DbCarouselDealUpdateComponent,
    resolve: {
      dbCarouselDeal: DbCarouselDealRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dbCarouselDealRoute)],
  exports: [RouterModule],
})
export class DbCarouselDealRoutingModule {}
