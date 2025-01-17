import 'reflect-metadata';
import compression from 'compression';
import cookieParser from 'cookie-parser';
import cors from 'cors';
import express from 'express';
import helmet from 'helmet';
import hpp from 'hpp';
import morgan from 'morgan';
import mongoose from 'mongoose';
import swaggerJSDoc from 'swagger-jsdoc';
import swaggerUi from 'swagger-ui-express';
import { NODE_ENV, PORT, LOG_FORMAT, ORIGIN, CREDENTIALS } from '@/config/index';
import { mysqlConnection } from '@/databases/mysql';
import { mongodbConnection } from '@/databases/mongodb';
import { Routes } from '@/interfaces/routes.interface';
import { ErrorMiddleware } from '@/middlewares/error.middleware';
import { logger, stream } from '@/utils/logger';
import { AdminRoute } from '@/routes/admin.route';
import { CustomerRoute } from '@/routes/customer.route';
import { startActivityLogConsumer } from './queue/consumer/activity-log.consumer';
import { rateLimitConfig, corsOptions, helmetOptions } from '@/config/security.config';
import { xssMiddleware } from '@/middlewares/security/xss.middleware';
import { sqlInjectionMiddleware } from '@/middlewares/security/sql-injection.middleware';
// import { csrfProtection, csrfErrorHandler } from '@/middlewares/security/csrf.middleware';

const app = express();
const env = NODE_ENV || 'development';
const port = PORT || 8000;

app.use(morgan(LOG_FORMAT, { stream }));
app.use(cors({ origin: ORIGIN, credentials: CREDENTIALS }));
app.use(hpp());
app.use(helmet(helmetOptions));
app.use(compression());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(ErrorMiddleware);
app.use(rateLimitConfig);
app.use(cors(corsOptions));
app.use(xssMiddleware);
app.use(sqlInjectionMiddleware);
// app.use(csrfProtection);
// app.use(csrfErrorHandler);

// Connect to MySQL Database
mysqlConnection
  .authenticate()
  .then(() => {
    logger.info('Connect to MySQL Database successfully');
    mysqlConnection.sync({ force: false });
  })
  .catch(error => {
    logger.error('Error connecting to MySQL:', error);
  });

// Connect to MongoDB Database
mongoose
  .connect(mongodbConnection.url)
  //.connect('mongodb+srv://pdcthanh112:vvhtFdU9MXlCNlpw@mydream.gwxdmti.mongodb.net/')
  .then(() => {
    logger.info('Connect to MongoDB successfully');
  })
  .catch(error => {
    logger.error('Error connecting to MongoDB:', error);
  });

//Define app router
const routes: Routes[] = [new AdminRoute(), new CustomerRoute()];
routes.forEach(route => {
  app.use('/', route.router);
});

const options = {
  swaggerDefinition: {
    info: {
      title: 'REST API',
      version: '1.0.0',
      description: 'Example docs',
    },
  },
  apis: ['swagger.yaml'],
};

const specs = swaggerJSDoc(options);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(specs));

// startActivityLogConsumer();

app.listen(port, () => {
  logger.info(`=================================`);
  logger.info(`======= ENV: ${env} =======`);
  logger.info(`🚀 App listening on the port ${port}`);
  logger.info(`=================================`);
});
