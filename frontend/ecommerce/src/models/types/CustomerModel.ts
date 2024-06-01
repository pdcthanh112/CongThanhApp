export type Customer = {
  userInfo: {
    id: number;
    email: string;
    password: string;
    accountId: string;
    name: string;
    phone: string;
    address: string;
    gender: string;
    dob: Date;
    image: string;
  };
  tokenData: {
    token: string;
    expiresIn: number;
  };
};

