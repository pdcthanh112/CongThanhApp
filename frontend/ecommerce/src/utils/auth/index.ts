import { Customer } from '@models/type';

export const LocalStorageEventTarget = new EventTarget();

export const setAccessTokenToLocalStorage = (access_token: string) => {
  localStorage.setItem('access_token', access_token);
};

export const clearLocalStorage = () => {
  localStorage.removeItem('access_token');
  localStorage.removeItem('profile');
  const clearStorageEvent = new Event('clearLS');
  LocalStorageEventTarget.dispatchEvent(clearStorageEvent);
};
export const getAccessTokenFromLocalStorage = () => localStorage.getItem('access_token') || '';

export const getProfileFromLocalStorage = () => {
  const result = localStorage.getItem('profile');
  return result ? JSON.parse(result) : null;
};

export const setProfileToLocalStorage = (profile: Customer) => {
  localStorage.setItem('profile', JSON.stringify(profile));
};
