import { config } from 'dotenv';
config({ path: `.env.${process.env.NODE_ENV || 'development'}` });

export const CREDENTIALS = process.env.CREDENTIALS === 'true';
export const { NODE_ENV, PORT, ACCESS_TOKEN_SECRET, REFRESH_TOKEN_SECRET, ACCESS_TOKEN_EXPIRED, REFRESH_TOKEN_EXPIRED, LOG_FORMAT, LOG_DIR, ORIGIN } = process.env;
export const { MYSQL_USER, MYSQL_PASSWORD, MYSQL_HOST, MYSQL_PORT, MYSQL_DATABASE } = process.env;
export const { MONGODB_HOST, MONGODB_PORT, MONGODB_DATABASE } = process.env;
export const { RATE_LIMIT_WINDOW, RATE_LIMIT_MAX_REQUESTS } = process.env;
