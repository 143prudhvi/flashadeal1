export interface IDealUserRelation {
  id?: number;
  userId?: string | null;
  dealId?: string | null;
}

export class DealUserRelation implements IDealUserRelation {
  constructor(public id?: number, public userId?: string | null, public dealId?: string | null) {}
}

export function getDealUserRelationIdentifier(dealUserRelation: IDealUserRelation): number | undefined {
  return dealUserRelation.id;
}
