import axiosConfig from '@/config/axiosConfig';
import { ChangePasswordForm, ResetPasswordForm } from '@/models/form';
import axios from 'axios';

export const login = async (email: string, password: string) => {
  return await axios
    .post('http://localhost:8000/ecommerce/auth/login', { email: email, password: password })
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const logout = async (email: string) => {
  return await axios
    .post('http://localhost:8000/ecommerce/auth/logout', { email: email })
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const signup = async (data: SignupForm) => {
  return await axios
    .post('http://localhost:8000/ecommerce/auth/signup', {
      email: data.email,
      name: data.name,
      password: data.password,
      address: data.address,
      phone: data.phone,
      gender: data.gender,
    })
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const changePassword = async (data: ChangePasswordForm) => {
  return await axios
    .post('http://localhost:8000/ecommerce/auth/change-password', {
      accountId: data.accountId,
      currentPassword: data.currentPassword,
      newPassword: data.newPassword,
    })
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const resetPassword = async (data: ResetPasswordForm) => {
  return await axios
    .post('http://localhost:8000/ecommerce/auth/reset-password', {
      accountId: data.accountId,
      password: data.newPassword,
    })
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const generateOTP = async (data: any) => {
  return await axios
    .get('http://localhost:8000/ecommerce/auth/generate-otp')
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const verifyOTP = async (data: any) => {
  return await axios
    .get('http://localhost:8000/ecommerce/auth/verify-otp')
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};
