import React, { useRef, useState } from 'react';
import { Button } from '@components/UI';
import styled from 'styled-components';
import { SubmitHandler, useForm } from 'react-hook-form';
import { ResetPasswordForm } from '@models/form';
import { Spin, Tooltip } from 'antd';
import ValidatePassword from '@components/ValidatePassword';
import Link from 'next/link';
import path from '@config/path';
import { Icon } from '@mui/material';
import { DoneAll, Password, Visibility, VisibilityOff } from '@mui/icons-material';
import { useTranslation } from 'next-i18next';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';

const InputField = styled.div`
  border: 1px solid #b6b6b6;
  padding: 0.5rem 0.2rem 0.5rem 0.4rem;
  border-radius: 4px;
  margin-top: 1rem;
  position: relative;
  width: 25rem;
`;

const ResetPassword = () => {
  const [otp, setOtp] = useState(new Array(6).fill(null));
  const [isVerity, setIsVerity] = useState(true);
  const otpBoxReference = useRef<any>([]);

  const [showNewPassword, setShowNewPassword] = useState<boolean>(false);
  const [showModalNewPassword, setShowModalNewPassword] = useState<boolean>(false);
  const [showModalConfirmPassword, setShowModalConfirmPassword] = useState<boolean>(false);

  const [newPassword, setNewPassword] = useState<string>('');
  const [confirmPassword, setConfirmPassword] = useState<string>('');
  const { t } = useTranslation('common');
  const { register, handleSubmit, formState, setError } = useForm<ResetPasswordForm>();
  const onSubmit: SubmitHandler<ResetPasswordForm> = (data) => {
    console.log('aaaaaaaaaaaa', data);
    if (data.newPassword === data.confirmPassword) {
      // changePassword(data);
    } else {
      setError('confirmPassword', { type: 'value', message: 'confirm password is not match' });
    }
  };

  const handleChange = (value: string, index: number) => {
    let newArr = [...otp];
    newArr[index] = value;
    setOtp(newArr);

    if (value && index < 6 - 1) {
      otpBoxReference.current[index + 1].focus();
    }
  };
  console.log('OOOOOOOOOOOOOOO', otp);
  const handleBackspaceAndEnter = (e: any, index: number) => {
    if (e.key === 'Backspace' && !e.target.value && index > 0) {
      otpBoxReference.current[index - 1].focus();
    }
    if (e.key === 'Enter' && e.target.value && index < 6 - 1) {
      otpBoxReference.current[index + 1].focus();
    }
  };

  const handleSubmitOTP = () => {
    console.log('SSSSSSSSSSSSSSSSs', otp);
    setIsVerity(true);
  };

  const resendOTP = () => {};

  const PasswordMatchingStatus = () => <div>{newPassword === confirmPassword ? <Icon component={DoneAll} color="success" /> : <Spin />}</div>;

  return (
    <div className="bg-white h-[37rem]">
      {isVerity ? (
        <div className="w-1/3 mx-auto pt-20">
          <form onSubmit={handleSubmit(onSubmit)} className="grid place-items-center border border-gray-400 rounded-md py-5">
            <h3 className="text-2xl font-medium">Reset password</h3>
            <InputField className={formState.errors.newPassword && 'bg-red-100'}>
              <Tooltip title={<ValidatePassword password={newPassword} />} placement="top" open={showModalNewPassword}>
                <Icon component={Password} />
                <input
                  type={showNewPassword ? 'text' : 'password'}
                  placeholder="Enter your new password"
                  {...register('newPassword', { required: 'Please your new password' })}
                  className={`focus:outline-none ml-3 w-[22rem] ${formState.errors.newPassword && 'bg-red-100'}`}
                  onChange={(e) => setNewPassword(e.target.value)}
                  onFocus={() => setShowModalNewPassword(true)}
                  onBlur={() => setShowModalNewPassword(false)}
                />
              </Tooltip>
              <Icon
                component={showNewPassword ? Visibility : VisibilityOff}
                titleAccess={showNewPassword ? t('common.hide_password') : t('common.show_password')}
                fontSize="small"
                onClick={() => setShowNewPassword(!showNewPassword)}
                className="absolute right-3 mt-1 hover:cursor-pointer"
              />
            </InputField>
            <InputField className={formState.errors.confirmPassword && 'bg-red-100'}>
              <Tooltip title={<PasswordMatchingStatus />} placement="rightTop" open={confirmPassword !== '' && showModalConfirmPassword}>
                <Icon component={Password} />
                <input
                  type={showNewPassword ? 'text' : 'password'}
                  placeholder="Enter confirm your password"
                  {...register('confirmPassword', { required: 'Please your current password' })}
                  className={`focus:outline-none ml-3 w-[22rem] ${formState.errors.confirmPassword && 'bg-red-100'}`}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  onFocus={() => setShowModalConfirmPassword(true)}
                  onBlur={() => setShowModalConfirmPassword(false)}
                />
              </Tooltip>
              <Icon
                component={showNewPassword ? Visibility : VisibilityOff}
                titleAccess={showNewPassword ? t('common.hide_password') : t('common.show_password')}
                fontSize="small"
                onClick={() => setShowNewPassword(!showNewPassword)}
                className="absolute right-3 mt-1 hover:cursor-pointer"
              />
            </InputField>

            <div className="flex justify-center mt-6">
              <Button className="bg-yellow-300 rounded-lg">{t('common.save')}</Button>
            </div>
          </form>
          <Link href={path.login} className='flex justify-end mt-3'>Return to Login</Link>
        </div>
      ) : (
        <article className="w-1/2 mx-auto pt-20">
          <p className="text-2xl font-medium">OTP Input With Validation</p>
          <p className="text-base text-white mt-4 bg-[#323232] p-4 rounded-md">We will send an OTP code to phone number (+84)38xxxxx49</p>

          <div className="flex justify-between">
            <div className="flex items-center gap-4 mt-2">
              {otp.map((value, index) => (
                <input
                  key={index}
                  value={value}
                  maxLength={1}
                  onChange={(e) => handleChange(e.target.value, index)}
                  onKeyUp={(e) => handleBackspaceAndEnter(e, index)}
                  ref={(reference) => (otpBoxReference.current[index] = reference)}
                  className={`border w-20 h-auto text-black text-center p-3 rounded-md block bg-gray-300 focus:border-2 focus:outline-none appearance-none`}
                />
              ))}
            </div>
            <Button className="w-28 h-12 mt-2 py-3 text-black text-center rounded-xl" onClick={() => handleSubmitOTP()}>
              Verify
            </Button>
          </div>
          <div>
            <span>You did not receive the otp code. </span>
            <span className="hover:underline hover:cursor-pointer hover:text-yellow-300" title="Resend" onClick={() => resendOTP()}>
              Resend
            </span>
          </div>
        </article>
      )}
    </div>
  );
};

export default ResetPassword;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}
