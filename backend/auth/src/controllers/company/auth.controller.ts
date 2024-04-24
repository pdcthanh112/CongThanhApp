import { NextFunction, Request, Response } from 'express';
import { Container } from 'typedi';
import { CandidateLoginDto } from '@/dtos/candidate.dto';
import { Candidate } from '@/interfaces/account.interface';
import { RequestWithUser } from '@interfaces/auth.interface';
import { AuthService } from '@services/company/auth.service';

export class AuthController {
  public service = Container.get(AuthService);

  public signUp = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userData: CandidateLoginDto = req.body;
      const signUpCandidateData: Candidate = await this.service.signup(userData);

      res.status(201).json({ data: signUpCandidateData, message: 'signup' });
    } catch (error) {
      next(error);
    }
  };

  public logIn = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const employeeData: CandidateLoginDto = req.body;
      const { cookie, findCandidate } = await this.service.login(employeeData);

      res.setHeader('Set-Cookie', [cookie]);
      res.status(200).json({ data: findCandidate, message: 'login' });
    } catch (error) {
      next(error);
    }
  };

  public logOut = async (req: RequestWithUser, res: Response, next: NextFunction) => {
    try {
      const userData: Candidate = req.user;
      const logOutUserData: Candidate = await this.service.logout(userData);

      res.setHeader('Set-Cookie', ['Authorization=; Max-age=0']);
      res.status(200).json({ data: logOutUserData, message: 'logout' });
    } catch (error) {
      next(error);
    }
  };
}