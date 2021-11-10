export interface IDealCategory {
  id?: number;
  name?: string | null;
  icon?: string | null;
  description?: string | null;
}

export class DealCategory implements IDealCategory {
  constructor(public id?: number, public name?: string | null, public icon?: string | null, public description?: string | null) {}
}

export function getDealCategoryIdentifier(dealCategory: IDealCategory): number | undefined {
  return dealCategory.id;
}
