import { IUser } from 'app/entities/user/user.model';
import { MemberType } from 'app/entities/enumerations/member-type.model';

export interface ILoginProfile {
  id?: number;
  memberType?: MemberType | null;
  phoneNumber?: string | null;
  emailId?: string | null;
  password?: string | null;
  activationCode?: string | null;
  user?: IUser | null;
}

export class LoginProfile implements ILoginProfile {
  constructor(
    public id?: number,
    public memberType?: MemberType | null,
    public phoneNumber?: string | null,
    public emailId?: string | null,
    public password?: string | null,
    public activationCode?: string | null,
    public user?: IUser | null
  ) {}
}

export function getLoginProfileIdentifier(loginProfile: ILoginProfile): number | undefined {
  return loginProfile.id;
}
