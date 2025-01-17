import { ActivityType } from '@/constants/enum';

export interface ActivityLog {
  id: number;
  accountId: string;
  activityType: ActivityType;
  ipAddress: string;
  createAt: Date;
}
