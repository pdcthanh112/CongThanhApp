'use client';

import axiosConfig from '@/config/axiosConfig';
import { signIn, useSession } from 'next-auth/react';

export const useRefreshToken = () => {
  const { data: session } = useSession();

  const refreshToken = async () => {
    const res = await axiosConfig.post('/auth/refresh', {
      refresh: session?.user?.refreshToken,
    });

    if (session) {
      session.user.accessToken = res.data.accessToken;
    } else {
      signIn();
    }
  };
  return refreshToken;
};
