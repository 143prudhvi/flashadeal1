import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DealUserRelationComponent } from './list/deal-user-relation.component';
import { DealUserRelationDetailComponent } from './detail/deal-user-relation-detail.component';
import { DealUserRelationUpdateComponent } from './update/deal-user-relation-update.component';
import { DealUserRelationDeleteDialogComponent } from './delete/deal-user-relation-delete-dialog.component';
import { DealUserRelationRoutingModule } from './route/deal-user-relation-routing.module';

@NgModule({
  imports: [SharedModule, DealUserRelationRoutingModule],
  declarations: [
    DealUserRelationComponent,
    DealUserRelationDetailComponent,
    DealUserRelationUpdateComponent,
    DealUserRelationDeleteDialogComponent,
  ],
  entryComponents: [DealUserRelationDeleteDialogComponent],
})
export class DealUserRelationModule {}
