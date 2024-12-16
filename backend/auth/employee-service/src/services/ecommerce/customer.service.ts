import { hash } from 'bcrypt';
import { Service } from 'typedi';
import { MYSQL_DB } from '@databases/mysql';
import { CreateCustomerDTO, UpdateCustomerDTO } from '@dtos/customer.dto';
import { HttpException } from '@exceptions/httpException';
import { Customer } from '@interfaces/account.interface';

@Service()
export class CustomerService {
  public async findAllCustomer(): Promise<Customer[]> {
    const allCustomer: Customer[] = await MYSQL_DB.Customer.findAll();
    return allCustomer;
  }

  public async findCustomerById(userId: number): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    if (!findCustomer) throw new HttpException(409, "Customer doesn't exist");

    return findCustomer;
  }

  public async createCustomer(userData: CreateCustomerDTO): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findOne({ where: { email: userData.email } });
    if (findCustomer) throw new HttpException(409, `This email ${userData.email} already exists`);

    const hashedPassword = await hash(userData.password, 10);
    const createCustomerData: Customer = await MYSQL_DB.Customer.create({ ...userData, password: hashedPassword });
    return createCustomerData;
  }

  public async updateCustomer(userId: number, userData: UpdateCustomerDTO): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    if (!findCustomer) throw new HttpException(409, "Customer doesn't exist");

    // const hashedPassword = await hash(userData.password, 10);
    // await DB.Customers.update({ ...userData, password: hashedPassword }, { where: { id: userId } });

    const updateCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    return updateCustomer;
  }

  public async deleteCustomer(userId: number): Promise<Customer> {
    const findCustomer: Customer = await MYSQL_DB.Customer.findByPk(userId);
    if (!findCustomer) throw new HttpException(409, "Customer doesn't exist");

    await MYSQL_DB.Customer.destroy({ where: { id: userId } });

    return findCustomer;
  }
}