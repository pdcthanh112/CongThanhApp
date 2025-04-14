import { NextResponse } from 'next/server';

export async function POST(req: Request) {
  const res = NextResponse.json({ message: 'Login success' });

  const data = await res.json()
  const token = data.accessToken

  res.cookies.set({
    name: 'access_token',
    value: token,
    httpOnly: true,      
    secure: true,
    path: '/',
    sameSite: 'strict',
    maxAge: 60 * 60 * 24,
  });

  return res;
}
