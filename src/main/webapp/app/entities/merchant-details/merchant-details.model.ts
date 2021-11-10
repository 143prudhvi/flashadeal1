import { MerchantLocationType } from 'app/entities/enumerations/merchant-location-type.model';

export interface IMerchantDetails {
  id?: number;
  name?: string | null;
  country?: string | null;
  city?: string | null;
  storeIcon?: string | null;
  type?: string | null;
  location?: MerchantLocationType | null;
}

export class MerchantDetails implements IMerchantDetails {
  constructor(
    public id?: number,
    public name?: string | null,
    public country?: string | null,
    public city?: string | null,
    public storeIcon?: string | null,
    public type?: string | null,
    public location?: MerchantLocationType | null
  ) {}
}

export function getMerchantDetailsIdentifier(merchantDetails: IMerchantDetails): number | undefined {
  return merchantDetails.id;
}
