import { hash } from 'bcrypt';
import { MYSQL_DB } from '@/databases/mysql';
import { CreateCustomerDTO, UpdateCustomerDTO } from '@/dtos/customer.dto';
import { HttpException } from '@/exceptions/http.exception';
import { Customer } from '@/interfaces/account.interface';

export class CustomerService {
  public async findAllCustomer(): Promise<Customer[]> {
    const allCustomer: Customer[] = await MYSQL_DB.Customer.findAll();
    return allCustomer;
  }

  public async findCustomerById(userId: number): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    if (!findCustomer) throw new HttpException(409, "Customer doesn't exist", 262462);

    return findCustomer;
  }

  public async createCustomer(userData: CreateCustomerDTO): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findOne({ where: { email: userData.email } });
    if (findCustomer) throw new HttpException(409, `This email ${userData.email} already exists`,23534);

    const hashedPassword = await hash(userData.password, 10);
    const createCustomerData: Customer = await MYSQL_DB.Customer.create({ ...userData, password: hashedPassword });
    return createCustomerData;
  }

  public async updateCustomer(userId: number, userData: UpdateCustomerDTO): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    if (!findCustomer) throw new HttpException(409, "Customer doesn't exist", 4254);

    // const hashedPassword = await hash(userData.password, 10);
    // await DB.Customers.update({ ...userData, password: hashedPassword }, { where: { id: userId } });

    const updateCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    return updateCustomer;
  }

  public async deleteCustomer(userId: number): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    if (!findCustomer) throw new HttpException(409, "Customer doesn't exist", 54236);

    await MYSQL_DB.Customer.destroy({ where: { id: userId } });

    return findCustomer;
  }
}