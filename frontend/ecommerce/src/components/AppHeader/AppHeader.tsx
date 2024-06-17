'use client';
import React from 'react';
import './AppHeader.scss';
import Image from 'next/image';
import { Card, Avatar, Icon } from '@mui/material';
import { useRouter } from 'next/navigation';
import { useAppDispatch, useAppSelector } from '@/redux/store';
import { Button } from '@/components/ui/button';
import { signIn, signOut, useSession } from 'next-auth/react';
import AppLogo from '@/assets/images/app-logo-removebg.png';
import DefaultImage from '@/assets/images/default-image.jpg';
import { Search as SearchIcon, ArrowDropDownOutlined, NavigateNext } from '@mui/icons-material';
import CartModal from '@/components/Cart/CartModal';
import LanguageSwitcher from '@/components/LanguageSwitcher';
import NotificationModal from '@/components/NotificationModal';
import { PATH } from '@/utils/constants/path';
import { NotificationIcon } from '@/assets/icons';
import { logoutRequested } from '@/redux/actions/auth';
import Link from 'next/link';
import { Category, Customer, Notification } from '@/models/types';
import { ThemeToggle } from '../Theme/ThemeToggle';
import { useTranslations } from 'next-intl';
import { useQuery } from '@tanstack/react-query';
import { NOTIFICATION_KEY } from '@/utils/constants/queryKey';
import { getNotificationByCustomer } from '@/api/notificationApi';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';

const AppHeader = () => {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const appCategory: Category[] = useAppSelector((state) => state.category.data);

  const dispatch = useAppDispatch();
  const router = useRouter();
  const t = useTranslations();
  const { data: session } = useSession();

  const { data: listNotification, isLoading } = useQuery({
    queryKey: [NOTIFICATION_KEY],
    queryFn: async () =>
      await getNotificationByCustomer('e6169aa0-d4a5-4740-b510-9285bbb73a0e').then((response) => response.data),
    // await getNotificationByCustomer(currentUser.userInfo.accountId).then((response) => response.data),
  });

  const handleLogout = () => {
    signOut();
    dispatch(logoutRequested({ email: 'fjasljflashfiahsd' }));
  };

  const handleSearch = () => {};

  return (
    <div className="flex items-center bg-slate-400 p-1 flex-grow py-2">
      <Link href={PATH.HOME}>
        <Image src={AppLogo} alt="App Logo" width={100} className="cursor-pointer mx-12" />
      </Link>

      <div className="hidden sm:flex items-center h-10 rounded-md flex-grow cursor-pointer">
        <span className="bg-gray-300 h-[2.5rem] w-20 rounded-l-md flex justify-center items-center relative group">
          <span className="">
            {t('common.all')}
            <ArrowDropDownOutlined />
          </span>

          <Card className="text-[#a4a4a4] text-sm hidden absolute transform translate-x-[36%] translate-y-[56%] py-2 w-[15rem] group-hover:block group-hover:z-50 max-h-96 group-hover:overflow-y-scroll">
            {appCategory?.map((item: Category) => (
              <div key={item.id} className="leading-6 px-2 hover:bg-gray-100">
                {item.name}
              </div>
            ))}
          </Card>
        </span>
        <input className="p-2 h-full w-6 flex-grow flex-shrink focus:outline-none px-4" />
        <SearchIcon className="!w-14 !h-10 p-1 bg-yellow-400 hover:bg-yellow-500 rounded-r-md" />
      </div>

      <div className="text-white flex items-center text-xs space-x-6 mx-6 whitespace-nowrap">
        <LanguageSwitcher />

        <CartModal />

        <ThemeToggle />

        <Popover>
          <PopoverTrigger asChild>
            <div className="flex items-start justify-center relative">
              <NotificationIcon width={30} height={30} className="hover:cursor-pointer" />
              {Number(listNotification?.length) > 0 && (
                <span className="absolute top-0 right-0 h-4 w-4 bg-yellow-400 text-center rounded-full text-black font-bold">
                  {listNotification?.filter((item) => item.isRead === false).length}
                </span>
              )}
            </div>
          </PopoverTrigger>
          <PopoverContent className="w-[25rem]">
            <NotificationModal listNotification={listNotification as Notification[]} loading={isLoading} />
          </PopoverContent>
        </Popover>

        <div className="relative inline-block group">
          <div className="hover:cursor-pointer">
            {session || currentUser ? (
              <div>
                <div>{/* {t('common.hello')}, {currentUser.userInfo.name.split(' ').pop()} */}</div>
                {/* <div className="font-semibold md:text-sm">{t('header.account_and_info')}</div> */}
              </div>
            ) : (
              <div>
                {/* <div>{t('common.welcome')}</div>
                <div className="font-semibold md:text-sm" onClick={() => router.push(PATH.AUTH_PATH_URL.LOGIN)}>
                  {t('common.login')} or {t('common.signup')}
                </div> */}
              </div>
            )}
          </div>
          <Card className="text-[#a4a4a4] text-sm hidden absolute transform -translate-x-3/4 p-4 w-[30rem] group-hover:block group-hover:z-50">
            {currentUser ? (
              <>
                <div className="flex justify-between bg-sky-100 px-5 py-3 rounded-md">
                  <span className="flex items-center">
                    <Avatar src={currentUser.userInfo.image || String(DefaultImage)} />
                    <span className="font-medium text-lg ml-3">{currentUser.userInfo.name}</span>
                  </span>
                  <Link
                    href={PATH.MANAGE_PATH_URL.PROFILE}
                    className="flex items-center hover:cursor-pointer hover:underline hover:text-yellow-600"
                  >
                    {/* {t('common.manage_profile')} */}
                    <Icon component={NavigateNext} />
                  </Link>
                </div>
                <div className="flex">
                  <div className="w-1/2">
                    {/* <h3 className="font-semibold text-base">{t('header.your_shopping')}</h3> */}
                    <menu className="leading-6">
                      {/* <MenuItem name={t('header.view_your_cart')} url={PATH.CART} />
                      <MenuItem name={t('header.orders')} url={PATH.ORDER} />
                      <MenuItem name={t('header.wishlist')} url={PATH.WISHLIST} />
                      <MenuItem name={t('header.history')} url={PATH.HISTORY} /> */}
                    </menu>
                  </div>
                  <div className="w-1/2 border-l-2 border-l-gray-100 pl-4">
                    {/* <h3 className="font-semibold text-base">{t('header.your_account')}</h3> */}
                    <menu className="leading-6">
                      {/* <MenuItem name={t('header.account')} url={PATH.MANAGE_PATH_URL.ACCOUNT} />
                      <MenuItem name={t('header.recommendations')} url={"#"} />
                      <MenuItem name={t('header.browsing_history')} url={'#'} />
                      <MenuItem name={t('header.watchlist')} url={'#'} /> */}
                    </menu>
                    <Button className="bg-yellow-400 w-52 rounded-xl" onClick={() => handleLogout()}>
                      {/* {t('common.logout')} */}
                    </Button>
                  </div>
                </div>
              </>
            ) : (
              <>Chua loginnn</>
            )}
          </Card>
        </div>
      </div>
    </div>
  );
};

const MenuItem = ({ name, url, className }: { name: string; url: string; className?: string }) => (
  <li>
    <Link href={url} className={`opacity-80 hover:opacity-90 hover:underline hover:text-blue-600 ${className}`}>
      {name}
    </Link>
  </li>
);

export default AppHeader;
