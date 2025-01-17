import { NextFunction, Request, Response } from 'express';
import { CustomerSignupDTO, UpdateCustomerDTO } from '@/dtos/customer.dto';
import { Customer } from '@/interfaces/account.interface';
import { CustomerService } from '@/services/customer.service';

export class CustomerController {
  private service = new CustomerService();

  public getAllCustomer = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const findAllCustomersData: Customer[] = await this.service.findAllCustomer();

      res.status(200).json({ data: findAllCustomersData, message: 'findAll' });
    } catch (error) {
      next(error);
    }
  };

  public getCustomerById = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userId = Number(req.params.id);
      const findOneCustomerData: Customer = await this.service.findCustomerById(userId);

      res.status(200).json({ data: findOneCustomerData, message: 'findOne' });
    } catch (error) {
      next(error);
    }
  };

  public createCustomer = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userData: CustomerSignupDTO = req.body;
      const createCustomerData: Customer = await this.service.createCustomer(userData);

      res.status(201).json({ data: createCustomerData, message: 'created' });
    } catch (error) {
      next(error);
    }
  };

  public updateCustomer = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userId = Number(req.params.id);
      const userData: UpdateCustomerDTO = req.body;
      const updateCustomerData: Customer = await this.service.updateCustomer(userId, userData);

      res.status(200).json({ data: updateCustomerData, message: 'updated' });
    } catch (error) {
      next(error);
    }
  };

  public deleteCustomer = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userId = Number(req.params.id);
      const deleteCustomerData: Customer = await this.service.deleteCustomer(userId);

      res.status(200).json({ data: deleteCustomerData, message: 'deleted' });
    } catch (error) {
      next(error);
    }
  };
}