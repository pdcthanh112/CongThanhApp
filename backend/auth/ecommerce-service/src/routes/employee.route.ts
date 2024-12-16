import { Router } from 'express';
import { AuthController } from '@controllers/management/auth.controller';
import { EmployeeController } from '@controllers/management/employee.controller';
// import { CreateEmployeeDto } from '@dtos/account.dto';
import { Routes } from '@interfaces/routes.interface';
import { AuthMiddleware } from '@middlewares/auth.middleware';
import { ValidationMiddleware } from '@middlewares/validation.middleware';

export class EmployeeRoute implements Routes {
  public path = '/management';
  public router = Router();
  public authController = new AuthController();
  public employeeController = new EmployeeController();

  constructor() {
    this.initializeRoutes();
  }

  private initializeRoutes() {
    this.router.post(`${this.path}/auth/login`, this.authController.login);
    //this.router.post(`${this.path}/signup`, this.authController.signup);
    this.router.post(`${this.path}/auth/logout`, AuthMiddleware, this.authController.logout);
    // this.router.post('/signup', ValidationMiddleware(EmployeeLoginDto, 'body'), this.authController.signUp);
    // this.router.post('/login', ValidationMiddleware(EmployeeLoginDto, 'body'), this.authController.logIn);

    this.router.get(`${this.path}`, this.employeeController.getAllEmployee);
    this.router.get(`${this.path}/:id(\\d+)`, this.employeeController.getEmployeeById);
    this.router.post(`${this.path}/create`, this.employeeController.createEmployee);
    //this.router.post(`${this.path}/create`, ValidationMiddleware(CreateUserDto, 'body'), this.user.createUser);
    //this.router.put(`${this.path}/:id(\\d+)`, ValidationMiddleware(CreateUserDto, 'body', true), this.employeeController.updateUser);
    this.router.delete(`${this.path}/:id(\\d+)`, this.employeeController.deleteEmployee);
  }
}
