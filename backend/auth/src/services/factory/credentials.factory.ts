import { CustomerLoginDTO } from "@/dtos/customer.dto";
import { OAuthFactory } from "./oauth.factory";

export class CredentialsOAuthFactory implements OAuthFactory {
    async login(loginData: CustomerLoginDTO): Promise<unknown> {
        
    }
  
    async refreshAccessToken(refreshToken: string): Promise<unknown> {
      return refreshToken;
    }
    async validateToken(token: string): Promise<boolean> {
      return !!token;
    }
    // ... other methods
  }