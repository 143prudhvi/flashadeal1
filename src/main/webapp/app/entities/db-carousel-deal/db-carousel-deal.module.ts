import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DbCarouselDealComponent } from './list/db-carousel-deal.component';
import { DbCarouselDealDetailComponent } from './detail/db-carousel-deal-detail.component';
import { DbCarouselDealUpdateComponent } from './update/db-carousel-deal-update.component';
import { DbCarouselDealDeleteDialogComponent } from './delete/db-carousel-deal-delete-dialog.component';
import { DbCarouselDealRoutingModule } from './route/db-carousel-deal-routing.module';

@NgModule({
  imports: [SharedModule, DbCarouselDealRoutingModule],
  declarations: [
    DbCarouselDealComponent,
    DbCarouselDealDetailComponent,
    DbCarouselDealUpdateComponent,
    DbCarouselDealDeleteDialogComponent,
  ],
  entryComponents: [DbCarouselDealDeleteDialogComponent],
})
export class DbCarouselDealModule {}
