import { NextFunction, Request, Response } from 'express';
import { Container } from 'typedi';
import { CreateCandidateDTO } from '@dtos/candidate.dto';
import { Candidate } from '@interfaces/account.interface';
import { CandidateService } from '@services/company/candidate.service';

export class CandidateController {
  public service = Container.get(CandidateService);

  public getAllCandidate = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const findAllCandidatesData: Candidate[] = await this.service.findAllCandidate();

      res.status(200).json({ data: findAllCandidatesData, message: 'findAll' });
    } catch (error) {
      next(error);
    }
  };

  public getCandidateById = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userId = Number(req.params.id);
      const findOneCandidateData: Candidate = await this.service.findCandidateById(userId);

      res.status(200).json({ data: findOneCandidateData, message: 'findOne' });
    } catch (error) {
      next(error);
    }
  };

  public createCandidate = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userData: CreateCandidateDTO = req.body;
      const createCandidateData: Candidate = await this.service.createCandidate(userData);

      res.status(201).json({ data: createCandidateData, message: 'created' });
    } catch (error) {
      next(error);
    }
  };

  public updateCandidate = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userId = Number(req.params.id);
      const userData: CreateCandidateDTO = req.body;
      const updateCandidateData: Candidate = await this.service.updateCandidate(userId, userData);

      res.status(200).json({ data: updateCandidateData, message: 'updated' });
    } catch (error) {
      next(error);
    }
  };

  public deleteCandidate = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const userId = Number(req.params.id);
      const deleteCandidateData: Candidate = await this.service.deleteCandidate(userId);

      res.status(200).json({ data: deleteCandidateData, message: 'deleted' });
    } catch (error) {
      next(error);
    }
  };
}