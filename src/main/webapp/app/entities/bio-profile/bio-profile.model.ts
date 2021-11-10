import { IUser } from 'app/entities/user/user.model';

export interface IBioProfile {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  dob?: string | null;
  gender?: string | null;
  imageUrl?: string | null;
  user?: IUser | null;
}

export class BioProfile implements IBioProfile {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public dob?: string | null,
    public gender?: string | null,
    public imageUrl?: string | null,
    public user?: IUser | null
  ) {}
}

export function getBioProfileIdentifier(bioProfile: IBioProfile): number | undefined {
  return bioProfile.id;
}
