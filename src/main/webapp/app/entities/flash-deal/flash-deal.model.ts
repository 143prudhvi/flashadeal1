import { IMerchantDetails } from 'app/entities/merchant-details/merchant-details.model';
import { IDealCategory } from 'app/entities/deal-category/deal-category.model';
import { DealType } from 'app/entities/enumerations/deal-type.model';
import { DealDisplayPostion } from 'app/entities/enumerations/deal-display-postion.model';

export interface IFlashDeal {
  id?: number;
  type?: DealType | null;
  title?: string | null;
  description?: string | null;
  imageUrl?: string | null;
  dealUrl?: string | null;
  postedBy?: string | null;
  postedDate?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  originalPrice?: string | null;
  currentPrice?: string | null;
  discount?: string | null;
  active?: string | null;
  position?: DealDisplayPostion | null;
  approved?: boolean | null;
  country?: string | null;
  city?: string | null;
  pinCode?: string | null;
  merchantDetails?: IMerchantDetails | null;
  dealCategory?: IDealCategory | null;
}

export class FlashDeal implements IFlashDeal {
  constructor(
    public id?: number,
    public type?: DealType | null,
    public title?: string | null,
    public description?: string | null,
    public imageUrl?: string | null,
    public dealUrl?: string | null,
    public postedBy?: string | null,
    public postedDate?: string | null,
    public startDate?: string | null,
    public endDate?: string | null,
    public originalPrice?: string | null,
    public currentPrice?: string | null,
    public discount?: string | null,
    public active?: string | null,
    public position?: DealDisplayPostion | null,
    public approved?: boolean | null,
    public country?: string | null,
    public city?: string | null,
    public pinCode?: string | null,
    public merchantDetails?: IMerchantDetails | null,
    public dealCategory?: IDealCategory | null
  ) {
    this.approved = this.approved ?? false;
  }
}

export function getFlashDealIdentifier(flashDeal: IFlashDeal): number | undefined {
  return flashDeal.id;
}
