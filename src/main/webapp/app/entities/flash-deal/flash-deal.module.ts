import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FlashDealComponent } from './list/flash-deal.component';
import { FlashDealDetailComponent } from './detail/flash-deal-detail.component';
import { FlashDealUpdateComponent } from './update/flash-deal-update.component';
import { FlashDealDeleteDialogComponent } from './delete/flash-deal-delete-dialog.component';
import { FlashDealRoutingModule } from './route/flash-deal-routing.module';

@NgModule({
  imports: [SharedModule, FlashDealRoutingModule],
  declarations: [FlashDealComponent, FlashDealDetailComponent, FlashDealUpdateComponent, FlashDealDeleteDialogComponent],
  entryComponents: [FlashDealDeleteDialogComponent],
})
export class FlashDealModule {}
