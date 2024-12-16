import { NextFunction, Request, Response } from 'express';
import { Container } from 'typedi';
import { EmployeeLoginDTO } from '@dtos/employee.dto';
import { Employee } from '@interfaces/account.interface';
import { RequestWithUser } from '@interfaces/auth.interface';
import { AuthService } from '@services/management/auth.service';

export class AuthController {
  private service = Container.get(AuthService);

  public login = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const employeeData: EmployeeLoginDTO = req.body;
      
      const { cookie, findEmployee } = await this.service.login(employeeData);

      res.setHeader('Set-Cookie', [cookie]);
      res.status(200).json({ data: findEmployee, message: 'login' });
    } catch (error) {
      next(error);
    }
  };

  // public signup = async (req: Request, res: Response, next: NextFunction) => {
  //   try {
  //     const employeeData: EmployeeLoginDto = req.body;
  //     const signUpEmployeeData: Employee = await this.service.signup(employeeData);

  //     res.status(201).json({ data: signUpEmployeeData, message: 'signup' });
  //   } catch (error) {
  //     next(error);
  //   }
  // };

  public logout = async (req: RequestWithUser, res: Response, next: NextFunction) => {
    try {
      const userData: Employee = req.user;
      const logOutUserData: Employee = await this.service.logout(userData);

      res.setHeader('Set-Cookie', ['Authorization=; Max-age=0']);
      res.status(200).json({ data: logOutUserData, message: 'logout' });
    } catch (error) {
      next(error);
    }
  };
}
