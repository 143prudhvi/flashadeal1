import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MerchantDetailsComponent } from './list/merchant-details.component';
import { MerchantDetailsDetailComponent } from './detail/merchant-details-detail.component';
import { MerchantDetailsUpdateComponent } from './update/merchant-details-update.component';
import { MerchantDetailsDeleteDialogComponent } from './delete/merchant-details-delete-dialog.component';
import { MerchantDetailsRoutingModule } from './route/merchant-details-routing.module';

@NgModule({
  imports: [SharedModule, MerchantDetailsRoutingModule],
  declarations: [
    MerchantDetailsComponent,
    MerchantDetailsDetailComponent,
    MerchantDetailsUpdateComponent,
    MerchantDetailsDeleteDialogComponent,
  ],
  entryComponents: [MerchantDetailsDeleteDialogComponent],
})
export class MerchantDetailsModule {}
