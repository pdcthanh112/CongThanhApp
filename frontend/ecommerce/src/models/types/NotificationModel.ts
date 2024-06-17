export type Notification = {
  id: number;
  customer: string;
  title: string;
  content: string;
  createdAt: Date;
  isRead: boolean;
  status: string
};
