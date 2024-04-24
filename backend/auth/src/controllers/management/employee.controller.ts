import { NextFunction, Request, Response } from 'express';
import { Container } from 'typedi';
import { CreateEmployeeDTO, UpdateEmployeeDTO } from '@dtos/employee.dto';
import { Employee } from '@interfaces/account.interface';
import { EmployeeService } from '@services/management/employee.service';

export class EmployeeController {
  private service = Container.get(EmployeeService);

  public getAllEmployee = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const result: Employee[] = await this.service.getAllEmployee();

      res.status(200).json({ data: result, message: 'findAll' });
    } catch (error) {
      next(error);
    }
  };

  public getEmployeeById = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const employeeId = Number(req.params.id);
      const result: Employee = await this.service.getEmployeeById(employeeId);

      res.status(200).json({ data: result, message: 'findOne' });
    } catch (error) {
      next(error);
    }
  };

  public createEmployee = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const employeeData: CreateEmployeeDTO = req.body;
      const createEmployeeData: Employee = await this.service.createEmployee(employeeData);

      res.status(201).json({ data: createEmployeeData, message: 'created' });
    } catch (error) {
      next(error);
    }
  };

  public updateEmployee = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const employeeId = Number(req.params.id);
      const employeeData: UpdateEmployeeDTO = req.body;
      const result: Employee = await this.service.updateEmployee(employeeId, employeeData);

      res.status(200).json({ data: result, message: 'updated' });
    } catch (error) {
      next(error);
    }
  };

  public deleteEmployee = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const employeeId = Number(req.params.id);
      const result: Employee = await this.service.deleteEmployee(employeeId);

      res.status(200).json({ data: result, message: 'deleted' });
    } catch (error) {
      next(error);
    }
  };
}
