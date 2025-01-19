import { CustomerLoginDTO } from "@/dtos/customer.dto";
import { AuthResponse, TokenData } from "@/interfaces/auth.interface";

export interface AuthStrategy {
  authenticate(credentials: CustomerLoginDTO): Promise<AuthResponse>;
  refreshAccessToken(token: string): Promise<TokenData>;
  validateToken(token: string): Promise<boolean>;
}
