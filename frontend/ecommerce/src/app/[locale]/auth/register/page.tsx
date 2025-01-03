import React from 'react';
import Image from 'next/image';
import LoginImage from '@/assets/images/login-page-image.png';
import RegisterForm from './RegisterForm';
import Link from 'next/link';
import { useTranslations } from 'next-intl';

export default function RegisterPage() {
  const t = useTranslations();

  return (
    <div className="grid grid-cols-12 h-full">
      <div className="col-span-5 relative">
        <Image src={LoginImage} alt={''} objectFit="fit" fill />
      </div>
      <div
        className="col-span-7 pt-10 overflow-y-scroll h-[40rem]"
        style={{
          scrollbarWidth: 'none', // Firefox
          msOverflowStyle: 'none', // IE and Edge
        }}
      >
        <div className="w-4/5 mx-auto my-10">
          <h3 className="mt-5 mb-5 font-medium text-xl">{t('common.welcome')} CongThanhApp - Ecommerce</h3>
          <div className="flex justify-end">
            {t('auth.you_already_have_an_account')}&nbsp;
            <Link href={'/auth/login'} className="text-green-400 hover:underline">
              {t('auth.login')}
            </Link>
          </div>
          <RegisterForm />
        </div>
      </div>
    </div>
  );
}
