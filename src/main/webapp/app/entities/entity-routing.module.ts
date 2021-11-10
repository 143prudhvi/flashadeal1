import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'login-profile',
        data: { pageTitle: 'LoginProfiles' },
        loadChildren: () => import('./login-profile/login-profile.module').then(m => m.LoginProfileModule),
      },
      {
        path: 'bio-profile',
        data: { pageTitle: 'BioProfiles' },
        loadChildren: () => import('./bio-profile/bio-profile.module').then(m => m.BioProfileModule),
      },
      {
        path: 'db-carousel-deal',
        data: { pageTitle: 'DbCarouselDeals' },
        loadChildren: () => import('./db-carousel-deal/db-carousel-deal.module').then(m => m.DbCarouselDealModule),
      },
      {
        path: 'side-display-deal',
        data: { pageTitle: 'SideDisplayDeals' },
        loadChildren: () => import('./side-display-deal/side-display-deal.module').then(m => m.SideDisplayDealModule),
      },
      {
        path: 'flash-deal',
        data: { pageTitle: 'FlashDeals' },
        loadChildren: () => import('./flash-deal/flash-deal.module').then(m => m.FlashDealModule),
      },
      {
        path: 'merchant-details',
        data: { pageTitle: 'MerchantDetails' },
        loadChildren: () => import('./merchant-details/merchant-details.module').then(m => m.MerchantDetailsModule),
      },
      {
        path: 'deal-category',
        data: { pageTitle: 'DealCategories' },
        loadChildren: () => import('./deal-category/deal-category.module').then(m => m.DealCategoryModule),
      },
      {
        path: 'deal-user-relation',
        data: { pageTitle: 'DealUserRelations' },
        loadChildren: () => import('./deal-user-relation/deal-user-relation.module').then(m => m.DealUserRelationModule),
      },
      {
        path: 'email-subscription',
        data: { pageTitle: 'EmailSubscriptions' },
        loadChildren: () => import('./email-subscription/email-subscription.module').then(m => m.EmailSubscriptionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
