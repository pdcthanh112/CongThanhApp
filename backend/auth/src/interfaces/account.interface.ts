import { LoginType } from "@/constants/enum";

export interface Account {
  id: number;
  accountId?: string;
  email: string;
  password: string;
  customerInfo?: Customer
  supplierInfo?: Supplier
  employeeInfo?: Employee
}

export interface Customer {
  id: number;
  accountId: string;
  name: string;
  email: string;
  password?: string;
  phone: string;
  phone_verified: boolean;
  address: string;
  dob: Date;
  gender: string;
  image?: string;
  provider: LoginType
}

export interface Supplier {
  id: number
}
export interface Employee {
  id?: number;
  accountId: string;
  empAccount: string;
  name: string;
  email: string;
  password?: string;
  phone: string;
  address: string;
  department: string;
  dob: Date;
  gender: string;
  image?: string;
  salary: number;
  refreshToken?: string;
}
