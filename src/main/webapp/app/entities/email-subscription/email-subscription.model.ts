export interface IEmailSubscription {
  id?: number;
  email?: string | null;
  country?: string | null;
}

export class EmailSubscription implements IEmailSubscription {
  constructor(public id?: number, public email?: string | null, public country?: string | null) {}
}

export function getEmailSubscriptionIdentifier(emailSubscription: IEmailSubscription): number | undefined {
  return emailSubscription.id;
}
