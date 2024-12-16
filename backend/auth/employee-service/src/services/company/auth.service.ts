import { compare, hash } from 'bcrypt';
import { sign } from 'jsonwebtoken';
import { Service } from 'typedi';
import { SECRET_KEY } from '@/config';
import { MYSQL_DB } from '@databases/mysql';
import { CandidateLoginDto } from '@/dtos/candidate.dto';
import { HttpException } from '@/exceptions/httpException';
import { DataStoredInToken, TokenData } from '@interfaces/auth.interface';
import { Candidate } from '@/interfaces/account.interface';

const createToken = (candidate: Candidate): TokenData => {
  const dataStoredInToken: DataStoredInToken = { id: candidate.id };
  const expiresIn: number = 60 * 60;

  return { expiresIn, token: sign(dataStoredInToken, SECRET_KEY, { expiresIn }) };
};

const createCookie = (tokenData: TokenData): string => {
  return `Authorization=${tokenData.token}; HttpOnly; Max-Age=${tokenData.expiresIn};`;
};

@Service()
export class AuthService {
  public async signup(userData: CandidateLoginDto): Promise<Candidate> {
    const findCandidate: Candidate = await MYSQL_DB.Candidate.findOne({ where: { email: userData.email } });
    if (findCandidate) throw new HttpException(409, `This email ${userData.email} already exists`);

    const hashedPassword = await hash(userData.password, 10);
    const createUserData: Candidate = await MYSQL_DB.Candidate.create({ ...userData, password: hashedPassword });

    return createUserData;
  }

  public async login(candidateData: CandidateLoginDto): Promise<{ cookie: string; findCandidate: Candidate }> {
    const findCandidate: Candidate = await MYSQL_DB.Candidate.findOne({ where: { email: candidateData.email } });
    if (!findCandidate) throw new HttpException(409, `This email ${candidateData.email} was not found`);

    const isPasswordMatching: boolean = await compare(candidateData.password, findCandidate.password);
    if (!isPasswordMatching) throw new HttpException(409, 'Password not matching');

    const tokenData = createToken(findCandidate);
    const cookie = createCookie(tokenData);

    return { cookie, findCandidate };
  }

  public async logout(candidateData: Candidate): Promise<Candidate> {
    const findCandidate: Candidate = await MYSQL_DB.Candidate.findOne({ where: { email: candidateData.email } });
    if (findCandidate) throw new HttpException(409, `This email ${candidateData.email} already exists`);
    return findCandidate;
  }
}