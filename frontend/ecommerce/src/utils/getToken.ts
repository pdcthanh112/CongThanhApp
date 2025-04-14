'use server';

import { cookies } from 'next/headers';

export const getToken = async (): Promise<string | null> => {
  if (typeof window === 'undefined') {
    // Server: đọc từ Next.js headers cookies (App Router)
    const cookieStore = await cookies();
    return cookieStore.get('access_token')?.value || null;
  } else {
    // Client: đọc từ document.cookie
    const match = document.cookie.match(/(?:^| )access_token=([^;]+)/);
    return match ? match[1] : null;
  }
};
