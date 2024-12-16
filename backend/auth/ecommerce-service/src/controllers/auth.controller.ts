import { NextFunction, Request, Response } from 'express';
import { CustomerSignupDTO, CustomerLoginDTO, ChangePasswordDTO } from '@/dtos/customer.dto';
import { Customer } from '@/interfaces/account.interface';
import { AuthService } from '@/services/auth.service';
import { RequestWithUser } from '@/interfaces/auth.interface';
import { OTP } from '@/interfaces/otp.interface';

export class AuthController {
  private service = new AuthService();

  public login = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const loginData: CustomerLoginDTO = req.body;
      const { customerWithoutPassword, tokenData } = await this.service.login(loginData);

      res.setHeader('Set-Cookie', [`Authorization=Bearer ${tokenData.accessToken}; HttpOnly;`]);
      res.setHeader('Authorization', `Bearer ${tokenData.accessToken}; HttpOnly;`);
      res.cookie('refreshToken', tokenData.refreshToken, { httpOnly: true, secure: process.env.NODE_ENV === 'production' });
      res.status(200).json({ data: { userInfo: customerWithoutPassword, tokenData: tokenData }, message: 'login successfully', status: 'SUCCESS' });
    } catch (error) {
      next(error);
    }
  };

  public signup = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const customerData: CustomerSignupDTO = req.body;
      const signUpCustomerData: Customer = await this.service.signup(customerData);

      res.status(201).json({ data: signUpCustomerData, message: 'signup successfully', status: 'SUCCESS' });
    } catch (error) {
      next(error);
    }
  };

  public logout = async (req: RequestWithUser, res: Response, next: NextFunction) => {
    try {
      const userData: Customer = req.user;
      const logoutUserData: Customer = await this.service.logout(userData);
      res.clearCookie('Authorization');
      res.setHeader('Set-Cookie', ['Authorization=; Max-age=0']);
      res.status(200).json({ data: logoutUserData, message: 'logout successfully', status: 'SUCCESS' });
    } catch (error) {
      next(error);
    }
  };

  public changePassword = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const data: ChangePasswordDTO = req.body;

      const result = await this.service.changePassword(data);

      res.status(200).json({ data: result, message: 'change password successfully', status: 'SUCCESS' });
    } catch (error) {
      next(error);
    }
  };

  public forgetPassword = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const data: { email: string } = req.body;
      const result = await this.service.forgetPassword({ email: data.email });
      res.status(200).json({ data: result, message: 'change password successfully', status: 'SUCCESS' });
    } catch (error) {
      next(error);
    }
  };

  public generateOTP = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const data: { phone: string } = req.body;
      const result: OTP = await this.service.generateOTP(data);
      res.status(200).json({ data: result, message: 'generate otp successfully', status: 'SUCCESS' });
    } catch (error) {
      next(error);
    }
  };

  public verifyOTP = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const data = req.body;
      const result = await this.service.verifyOTP(data);
      res.status(200).json({ data: result, message: 'verify OTP successfully', status: 'SUCCESS' });
    } catch (error) {
      next(error);
    }
  };

  public refreshAccessToken = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const refreshToken = req.header['Authorization'].split('Bearer ')[1];
      const { provider } = req.body;
      if (refreshToken) {
        const token = await this.service.refreshAccessToken(refreshToken, provider);
        res.status(200).json({ data: token, message: 'Refresh token successfully', status: 'SUCCESS' });
      } else {
        res.status(401).json({ error: 'Authorization header is missing or invalid' });
      }
    } catch (error) {
      next(error);
    }
  };

  public revokeToken = async (req: Request, res: Response, next: NextFunction) => {
    try {
      console.log("first")
    } catch (error) {
      next(error)
    }
  };
}
