export interface UserPermission {
  id: number;
  user: string;
  action: string;
  resource: string;
  created_at: Date;
  expired_at: Date;
}
