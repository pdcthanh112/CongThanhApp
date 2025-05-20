// export const getRequestURL = (url: string) => {
//   if (!url.startsWith('/')) {
//     url = `/${url}`;
//   }
//   return `${process.env.NEXT_APP_API_URL}${url}`;
// };

// const getAccessToken = async () => {
//   const res = await fetch('/api/auth/token');
//   if (res.ok) {
//     return await res.json();
//   } else if (res.status === 401) {
//     const res = await fetch('/api/refresh');
//     if (res.ok) {
//       const { accessToken } = await res.json();
//       return accessToken;
//     }
//   }
//   return '';
// };

// const httpRequest = async <Response>(
//   url: string,
//   method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE',
//   data: TEntity,
//   options: RequestInit = {}
// ) => {
//   let request_body = data;
//   const token = await getAccessToken();
//   if (!options.headers && method !== 'GET') {
//     options.headers = {
//       'Content-Type': 'application/json',
//     };
//     request_body = JSON.stringify(data);
//   }
//   options.headers = {
//     Accept: 'application/json',
//     ...options.headers,
//     ...{
//       Authorization: `Bearer ${token}`,
//     },
//   };
//   const requestOptions: RequestInit = {
//     method,
//     ...(method !== 'GET' && { cache: 'no-store' }),
//     body: request_body as any,
//     ...options,
//   };

//   const res = await fetch(getRequestURL(url), requestOptions);

//   if (!res.ok) {
//     if (res.status >= 500) throw new Error(`Error occured when calling API: ${url}`);
//     const body = await res.json();
//     throw new Error(body.detail);
//   }
//   const body = await res.json();
//   return body;
// };

// export const http = {
//   get: (url: string, options?: RequestInit) => httpRequest<Response>(url, 'GET', undefined, options),
//   post: <TEntity>(url: string, data: TEntity, options?: RequestInit) =>
//     httpRequest<Response>(url, 'POST', data, options),
//   put: <TEntity>(url: string, data: TEntity, options?: RequestInit) => httpRequest<Response>(url, 'PUT', data, options),
//   patch: <TEntity>(url: string, data: TEntity, options?: RequestInit) =>
//     httpRequest<Response>(url, 'PATCH', data, options),
//   delete: (url: string, options?: RequestInit) => httpRequest<Response>(url, 'DELETE', undefined, options),
// };

import { cookies } from 'next/headers';
import { ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from '@/utils/constants/cookies';

export const getRequestURL = (url: string): string => {
  if (!url.startsWith('/')) {
    url = `/${url}`;
  }
  return `${process.env.NEXT_APP_API_URL}${url}`;
};

let isRefreshing = false;
let refreshPromise: Promise<string> | null = null;

interface RequestData {
  [key: string]: unknown;
}

interface ResponseData {
  [key: string]: unknown;
}

const httpRequest = async <TResponse extends ResponseData>(
  url: string,
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE',
  data?: RequestData,
  options: RequestInit = {}
): Promise<TResponse> => {
  const cookieStore = await cookies();
  let accessToken = cookieStore.get(ACCESS_TOKEN_KEY)?.value;

  let headers: Record<string, string> = {};

  if (options.headers) {
    if (Array.isArray(options.headers)) {
      headers = Object.fromEntries(options.headers);
    } else if (options.headers instanceof Headers) {
      headers = Object.fromEntries(options.headers.entries());
    } else {
      headers = options.headers as Record<string, string>;
    }
  }

  if (accessToken) {
    headers = {
      ...headers,
      Accept: 'application/json',
      Authorization: `Bearer ${accessToken}`,
    };
  } else {
    headers = {
      ...headers,
      Accept: 'application/json',
    };
  }

  if (method !== 'GET' && !headers['Content-Type']) {
    headers = {
      ...headers,
      'Content-Type': 'application/json',
    };
  }

  const requestOptions: RequestInit = {
    method,
    ...(method !== 'GET' && { cache: 'no-store' }),
    ...(data && { body: JSON.stringify(data) }),
    ...options,
    headers,
  };

  const res = await fetch(getRequestURL(url), requestOptions);

  if (!res.ok) {
    const errorText = await res.text();
    if (res.status === 401 && accessToken && !isRefreshing) {
      isRefreshing = true;
      const refreshToken = cookieStore.get(REFRESH_TOKEN_KEY)?.value;

      if (!refreshToken) {
        throw new Error('No refresh token available');
      }

      refreshPromise = fetch('/api/auth/refresh', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ refreshToken }),
      })
        .then((res) => {
          if (!res.ok) throw new Error('Refresh failed');
          return res.json();
        })
        .then((data) => {
          const newAccessToken = (data as { accessToken: string }).accessToken;
          cookieStore.set('accessToken', newAccessToken, {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: 'strict',
            path: '/',
            maxAge: 15 * 60, // 15 phút
          });
          isRefreshing = false;
          refreshPromise = null;
          return newAccessToken;
        })
        .catch((err) => {
          isRefreshing = false;
          refreshPromise = null;
          throw err;
        });

      accessToken = await refreshPromise;
      headers.Authorization = `Bearer ${accessToken}`;
      return httpRequest(url, method, data, { ...options, headers });
    }

    if (res.status >= 500) {
      throw new Error(`Server error calling API: ${url} - ${errorText}`);
    }
    try {
      const errorBody = JSON.parse(errorText) as { detail?: string };
      throw new Error(errorBody.detail || errorText);
    } catch {
      throw new Error(`API error: ${errorText}`);
    }
  }

  const response = await res.json();
  return response as TResponse;
};

export const http = {
  get: <TResponse extends ResponseData>(url: string, options?: RequestInit) =>
    httpRequest<TResponse>(url, 'GET', undefined, options),
  post: <TResponse extends ResponseData, TEntity extends RequestData>(
    url: string,
    data: TEntity,
    options?: RequestInit
  ) => httpRequest<TResponse>(url, 'POST', data, options),
  put: <TResponse extends ResponseData, TEntity extends RequestData>(
    url: string,
    data: TEntity,
    options?: RequestInit
  ) => httpRequest<TResponse>(url, 'PUT', data, options),
  patch: <TResponse extends ResponseData, TEntity extends RequestData>(
    url: string,
    data: TEntity,
    options?: RequestInit
  ) => httpRequest<TResponse>(url, 'PATCH', data, options),
  delete: <TResponse extends ResponseData>(url: string, options?: RequestInit) =>
    httpRequest<TResponse>(url, 'DELETE', undefined, options),
};
