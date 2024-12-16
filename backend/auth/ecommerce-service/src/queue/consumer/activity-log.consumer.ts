import { saveActivityLog } from '@/listener/activity-log.listener';
import { createChannel } from '../rabbitmq';

export async function startActivityLogConsumer() {
  const channel = await createChannel();
  const queue = 'activity_logs';
  await channel.assertQueue(queue, { durable: true });

  channel.consume(queue, async (msg) => {
    if (msg) {
      const activityLog = JSON.parse(msg.content.toString());
      console.log('Processing activity log:', activityLog);

      try {
        await saveActivityLog(activityLog);
        channel.ack(msg); // Confirm successful processing
      } catch (error) {
        console.error('Failed to process activity log:', error);
        channel.nack(msg); // Retry the message later
      }
    }
  });

  console.log('Activity log consumer started');
}
