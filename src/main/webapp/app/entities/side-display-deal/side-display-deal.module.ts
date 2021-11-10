import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SideDisplayDealComponent } from './list/side-display-deal.component';
import { SideDisplayDealDetailComponent } from './detail/side-display-deal-detail.component';
import { SideDisplayDealUpdateComponent } from './update/side-display-deal-update.component';
import { SideDisplayDealDeleteDialogComponent } from './delete/side-display-deal-delete-dialog.component';
import { SideDisplayDealRoutingModule } from './route/side-display-deal-routing.module';

@NgModule({
  imports: [SharedModule, SideDisplayDealRoutingModule],
  declarations: [
    SideDisplayDealComponent,
    SideDisplayDealDetailComponent,
    SideDisplayDealUpdateComponent,
    SideDisplayDealDeleteDialogComponent,
  ],
  entryComponents: [SideDisplayDealDeleteDialogComponent],
})
export class SideDisplayDealModule {}
