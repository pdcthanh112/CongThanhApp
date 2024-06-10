import React from 'react';
import { Customer } from '@/models/types';
import { useAppSelector } from '@/redux/store';
import Image from 'next/image';
import DefaultImage from '@/assets/images/default-image.jpg';
import Link from 'next/link';

import { useTranslations } from 'next-intl';
import { useRouter } from 'next/router';
import { Icon } from '@mui/material';
import { AccountBox, ShoppingCart, Favorite, Lock, AddCard, LocationOn } from '@mui/icons-material';

type NavBarItemProps = {
  name: string;
  icon: any;
  url: string;
  className?: string;
};

const ManageNavbar = () => {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const router = useRouter();
  const { pathname } = router;
  const t = useTranslations('common');

  const NavBarItem = ({ name, icon, url, className }: NavBarItemProps) => (
    // <Card className={`px-3 py-4 rounded mb-2 hover:bg-blue-200  ${pathname === url && 'bg-blue-200'} ${className}`} sx={{backgroundColor: pathname === url ? '#4d4dff' : '#fff'}}>
    <Link href={url} className={`flex px-3 py-4 rounded mb-2 hover:bg-blue-100  ${pathname === url ? 'bg-blue-200' : 'bg-white'} ${className}`}>
      <Icon component={icon} />
      <span className="ml-1">{name}</span>
    </Link>
    // </Card>
  );

  return (
    <div className="flex flex-col">
      <div className="bg-blue-200 rounded flex px-3 py-2 border-b-2 border-gray-300 pb-2 mb-3">
        <Image src={currentUser.userInfo.image || DefaultImage} alt={currentUser.userInfo.name} width={60} height={60} className="rounded-[100%]" />
        <div className="flex flex-col ml-3">
          <div className="font-semibold flex items-center">{currentUser.userInfo.name}</div>
          <div className="flex flex-col text-sm">
            <span>{currentUser.userInfo.email}</span>
            <span>{currentUser.userInfo.phone}</span>
          </div>
        </div>
      </div>
      <div className="flex flex-col">
        <NavBarItem name={t('manage.profile')} icon={AccountBox} url={path.profile} />

        <div className="ml-3 flex flex-col">
          <NavBarItem name={'Change password'} icon={Lock} url={path.changePassword} />
          <NavBarItem name={'Banking account'} icon={AddCard} url={path.bankingAccount} />
          <NavBarItem name={'Address'} icon={LocationOn} url={path.address} />
        </div>
      </div>
      <NavBarItem name={'Cart'} icon={ShoppingCart} url={path.cart} />

      <NavBarItem name="Order" icon={ShoppingCart} url={path.order} />

      <NavBarItem name="Wishlist" icon={Favorite} url={path.wishlist} />
    </div>
  );
};

export default ManageNavbar;
