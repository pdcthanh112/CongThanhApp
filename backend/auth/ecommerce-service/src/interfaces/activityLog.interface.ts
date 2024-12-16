import { ActivityType } from '@/utils/enum';

export interface ActivityLog {
  id: number;
  accountId: string;
  activityType: ActivityType;
  ipAddress: string;
  createAt: Date;
}
