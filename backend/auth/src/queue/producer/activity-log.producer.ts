import { ActivityLog } from '@/interfaces/activityLog.interface';
import { createChannel } from '../rabbitmq';

export async function sendActivityLogMessage(activityLog: ActivityLog) {
  const channel = await createChannel();
  const queue = 'activity_logs';
  await channel.assertQueue(queue, { durable: true });
  channel.sendToQueue(queue, Buffer.from(JSON.stringify(activityLog)), { persistent: true });
  console.log('Activity log event sent:', activityLog);
}
