export interface JwtPayload {
    sub: string;        // userId từ auth-service
    email: string;      // email người dùng
    iat?: number;       // thời điểm token được tạo
    exp?: number;       // thời điểm token hết hạn
  }