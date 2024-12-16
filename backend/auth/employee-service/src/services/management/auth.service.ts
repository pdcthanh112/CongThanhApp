import { compare, hash } from 'bcrypt';
import { sign } from 'jsonwebtoken';
import { Service } from 'typedi';
import { SECRET_KEY } from '@/config';
import { MYSQL_DB } from '@databases/mysql';
import { EmployeeLoginDTO } from '@dtos/employee.dto';
import { HttpException } from '@exceptions/httpException';
import { DataStoredInToken, TokenData } from '@interfaces/auth.interface';
import { Employee } from '@interfaces/account.interface';

const createToken = (employee: Employee): TokenData => {
  const dataStoredInToken: DataStoredInToken = { id: employee.id };
  const expiresIn: number = 60 * 60;

  return { expiresIn, token: sign(dataStoredInToken, SECRET_KEY, { expiresIn }) };
};

const createCookie = (tokenData: TokenData): string => {
  return `Authorization=${tokenData.token}; HttpOnly; Max-Age=${tokenData.expiresIn};`;
};

@Service()
export class AuthService {
  public async login(employeeData: EmployeeLoginDTO): Promise<{ cookie: string; findEmployee: Employee }> {
    const findEmployee: Employee = await MYSQL_DB.Employee.findOne({ where: { email: employeeData.email } });
    if (!findEmployee) throw new HttpException(409, `This email ${employeeData.email} was not found`);

    const isPasswordMatching: boolean = await compare(employeeData.password, findEmployee.password);
    if (!isPasswordMatching) throw new HttpException(409, 'Password is incorrect');

    const tokenData = createToken(findEmployee);
    const cookie = createCookie(tokenData);

    return { cookie, findEmployee };
  }

  // public async signup(userData: EmployeeLoginDto): Promise<Employee> {
  //   const findEmployee: Employee = await MYSQL_DB.Employee.findOne({ where: { email: userData.email } });
  //   if (findEmployee) throw new HttpException(409, `This email ${userData.email} already exists`);

  //   const hashedPassword = await hash(userData.password, 10);
  //   const createUserData: Employee = await MYSQL_DB.Employee.create({ ...userData, password: hashedPassword });

  //   return createUserData;
  // }

  public async logout(employeeData: Employee): Promise<Employee> {
    const findEmployee: Employee = await MYSQL_DB.Employee.findOne({ where: { email: employeeData.email } });
    if (findEmployee) throw new HttpException(409, `This email ${employeeData.email} already exists`);
    return findEmployee;
  }
}
