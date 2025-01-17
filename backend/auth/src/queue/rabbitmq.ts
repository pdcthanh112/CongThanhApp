import amqp from 'amqplib';

let connection: amqp.Connection | null = null;

export async function getRabbitMQConnection() {
  if (!connection) {
    connection = await amqp.connect(process.env.RABBITMQ_URL || 'amqp://localhost');
  }
  return connection;
}

export async function createChannel() {
  const conn = await getRabbitMQConnection();
  return await conn.createChannel();
}
