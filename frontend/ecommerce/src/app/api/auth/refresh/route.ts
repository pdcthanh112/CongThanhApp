import { cookies } from 'next/headers';
import jwt from 'jsonwebtoken';

export async function POST(req) {
  const refreshToken = cookies().get('refreshToken')?.value;
  if (!refreshToken) return Response.json({ error: 'No refresh token' }, { status: 401 });

  try {
    const decoded = jwt.verify(refreshToken, process.env.REFRESH_TOKEN_SECRET);
    const newAccessToken = jwt.sign({ userId: decoded.userId }, process.env.ACCESS_TOKEN_SECRET, { expiresIn: '15m' });

    return Response.json({ accessToken: newAccessToken });
  } catch (error) {
    return Response.json({ error: 'Invalid refresh token' + error }, { status: 403 });
  }
}
