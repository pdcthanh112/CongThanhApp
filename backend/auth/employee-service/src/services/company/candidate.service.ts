import { hash } from 'bcrypt';
import { Service } from 'typedi';
import { MYSQL_DB } from '@databases/mysql';
import { CreateCandidateDTO } from '@dtos/candidate.dto';
import { HttpException } from '@exceptions/httpException';
import { Candidate } from '@interfaces/account.interface';

@Service()
export class CandidateService {
  public async findAllCandidate(): Promise<Candidate[]> {
    const allCandidate: Candidate[] = await MYSQL_DB.Candidate.findAll();
    return allCandidate;
  }

  public async findCandidateById(userId: number): Promise<Candidate> {
    const findCandidate: Candidate = await MYSQL_DB.Candidate.findByPk(userId);
    if (!findCandidate) throw new HttpException(409, "Candidate doesn't exist");

    return findCandidate;
  }

  public async createCandidate(userData: CreateCandidateDTO): Promise<Candidate> {
    const findCandidate: Candidate = await MYSQL_DB.Candidate.findOne({ where: { email: userData.email } });
    if (findCandidate) throw new HttpException(409, `This email ${userData.email} already exists`);

    const hashedPassword = await hash(userData.password, 10);
    const createCandidateData: Candidate = await MYSQL_DB.Candidate.create({ ...userData, password: hashedPassword });
    return createCandidateData;
  }

  public async updateCandidate(userId: number, userData: CreateCandidateDTO): Promise<Candidate> {
    const findCandidate: Candidate = await MYSQL_DB.Candidate.findByPk(userId);
    if (!findCandidate) throw new HttpException(409, "Candidate doesn't exist");

    const hashedPassword = await hash(userData.password, 10);
    await MYSQL_DB.Candidate.update({ ...userData, password: hashedPassword }, { where: { id: userId } });

    const updateCandidate: Candidate = await MYSQL_DB.Candidate.findByPk(userId);
    return updateCandidate;
  }

  public async deleteCandidate(userId: number): Promise<Candidate> {
    const findCandidate: Candidate = await MYSQL_DB.Candidate.findByPk(userId);
    if (!findCandidate) throw new HttpException(409, "Candidate doesn't exist");

    await MYSQL_DB.Candidate.destroy({ where: { id: userId } });

    return findCandidate;
  }
}