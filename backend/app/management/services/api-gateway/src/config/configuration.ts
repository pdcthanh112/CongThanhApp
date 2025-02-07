export default () => ({
  port: parseInt(process.env.PORT, 10) || 3000,
  cors: {
    origin: process.env.CORS_ORIGIN || '*',
  },
  auth: {
    host: process.env.AUTH_SERVICE_HOST || 'localhost',
    port: parseInt(process.env.AUTH_SERVICE_PORT, 10) || 3001,
  },
  user: {
    host: process.env.USER_SERVICE_HOST || 'localhost',
    port: parseInt(process.env.USER_SERVICE_PORT, 10) || 3002,
  },
  post: {
    host: process.env.POST_SERVICE_HOST || 'localhost',
    port: parseInt(process.env.POST_SERVICE_PORT, 10) || 3003,
  },
  media: {
    host: process.env.MEDIA_SERVICE_HOST || 'localhost',
    port: parseInt(process.env.MEDIA_SERVICE_PORT, 10) || 3004,
  },
  notification: {
    host: process.env.NOTIFICATION_SERVICE_HOST || 'localhost',
    port: parseInt(process.env.NOTIFICATION_SERVICE_PORT, 10) || 3005,
  },
  jwt: {
    secret: process.env.JWT_SECRET || 'secret',
    expiresIn: process.env.JWT_EXPIRES_IN || '1d',
  },
});
