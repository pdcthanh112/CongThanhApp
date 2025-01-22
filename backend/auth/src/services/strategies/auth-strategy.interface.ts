import { CustomerLoginDTO } from "@/dtos/customer.dto";
import { AuthResponse, TokenData } from "@/interfaces/auth.interface";

export interface AuthStrategy {
  authenticate(credentials: CustomerLoginDTO): Promise<AuthResponse>;
  refreshAccessToken(token: string): Promise<TokenData>;
  signOut(token: string): Promise<void>;
}
