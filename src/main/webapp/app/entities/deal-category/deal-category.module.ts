import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DealCategoryComponent } from './list/deal-category.component';
import { DealCategoryDetailComponent } from './detail/deal-category-detail.component';
import { DealCategoryUpdateComponent } from './update/deal-category-update.component';
import { DealCategoryDeleteDialogComponent } from './delete/deal-category-delete-dialog.component';
import { DealCategoryRoutingModule } from './route/deal-category-routing.module';

@NgModule({
  imports: [SharedModule, DealCategoryRoutingModule],
  declarations: [DealCategoryComponent, DealCategoryDetailComponent, DealCategoryUpdateComponent, DealCategoryDeleteDialogComponent],
  entryComponents: [DealCategoryDeleteDialogComponent],
})
export class DealCategoryModule {}
