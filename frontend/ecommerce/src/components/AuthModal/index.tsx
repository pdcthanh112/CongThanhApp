'use client';
import { useState } from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { Modal, Box, Icon } from '@mui/material';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useAppDispatch } from '@/redux/store';
import { loginRequested } from '@/redux/actions/auth';
import { closeModalAuth } from '@/redux/features/modalAuth';
import { useTranslations } from 'next-intl';
import { getAuthLogo } from '@/utils/helper';
import { signIn } from 'next-auth/react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import LoginComponent from './LoginComponent';
import RegisterComponent from './RegisterComponent';
import { IMAGE } from '@/utils/constants/path';

const AuthModal = () => {
  const dispatch = useAppDispatch();
  const t = useTranslations();

  const providers = [
    { id: 'google', name: 'Google' },
    { id: 'facebook', name: 'Facebook' },
    { id: 'twitter', name: 'Twitter' },
    { id: 'apple', name: 'Apple' },
  ];

  const [openAuthModal, setOpenAuthModal] = useState(true);

  return (
    <Modal
      open={openAuthModal}
      onClose={() => {
        setOpenAuthModal(false);
        dispatch(closeModalAuth());
      }}
    >
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 500,
          bgcolor: '#f1f2f6',
          boxShadow: 24,
        }}
      >
        <Tabs defaultValue="login" className="w-full">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="login">Login</TabsTrigger>
            <TabsTrigger value="register">Register</TabsTrigger>
          </TabsList>
          <TabsContent value="login">
            <Card>
              <CardHeader>
                <CardTitle>Login</CardTitle>
                <CardDescription className='flex justify-center'>
                  <Image src={IMAGE.appLogo} alt={''} width={200} height={100}/>
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-2">
                <LoginComponent/>

                <div className="flex justify-between mt-5">
                  {providers.map((item: any) => {
                    const data = getAuthLogo(item.id);
                    return (
                      <div
                        key={item.id}
                        title={`Login with ${item.name}`}
                        className={`flex items-center px-3 py-2 rounded-sm text-white font-medium hover:cursor-pointer bg-[${data.bgColor}]`}
                        onClick={() => signIn(item.id)}
                      >
                        <Image src={data.img} alt={''} width={20} />
                        <span className="ml-1 text-sm">{item.name}</span>
                      </div>
                    );
                  })}
                </div>
                <div className="my-3 font-medium">
                  <span>{t('login.you_dont_have_an_account')}&nbsp;</span>
                  <Link href="/auth/signup" style={{ color: '#116835' }}>
                    {t('signup.register')}
                  </Link>
                </div>
              </CardContent>
            </Card>
          </TabsContent>
          <TabsContent value="register">
            <Card>
              <CardHeader>
                <CardTitle>Register</CardTitle>
                <CardDescription>Change your password here. After saving, you&lsquo;ll be logged out.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-2">
                <RegisterComponent/>
                <div className="my-3 font-medium">
                  <span>{t('signup.you_already_have_an_account')}&nbsp;</span>
                  <Link href="/auth/login" style={{ color: '#116835' }}>
                    {t('common.login')}
                  </Link>
                </div>
              </CardContent>
            </Card>
          </TabsContent>
        </Tabs>
      </Box>
    </Modal>
  );
};

export default AuthModal;
