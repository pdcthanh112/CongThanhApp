'use client';
import { useState } from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { Modal, Box } from '@mui/material';
import { useAppDispatch } from '@/redux/store';
import { closeModalAuth } from '@/redux/features/modalAuth';
import { useTranslations } from 'next-intl';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import LoginComponent from './LoginComponent';
import RegisterComponent from './RegisterComponent';
import { IMAGE, PATH } from '@/utils/constants/path';
import { X } from 'lucide-react';

const AuthModal = () => {
  const dispatch = useAppDispatch();
  const t = useTranslations();

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
          bgcolor: '#fff',
          boxShadow: 24,
          borderRadius: 1,
          height: 600,
          overflowY: 'scroll',
          scrollbarWidth: 'none',
          msOverflowStyle: 'none',
        }}
      >
        <Tabs defaultValue="login" className="w-full relative">
          <TabsList className="grid w-3/5 grid-cols-2">
            <TabsTrigger value="login">{t('auth.login')}</TabsTrigger>
            <TabsTrigger value="register">{t('auth.register')}</TabsTrigger>
            <X className="absolute top-3 right-3 hover:cursor-pointer" onClick={() => setOpenAuthModal(false)} />
          </TabsList>
          <TabsContent value="login">
            <Card>
              <CardHeader>
                <CardTitle>{t('auth.login')}</CardTitle>
                <CardDescription className="flex justify-center">
                  <Image src={IMAGE.appLogo} alt={''} width={200} height={100} />
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-2">
                <LoginComponent />
                <div className="my-3 font-medium">
                  <span>{t('auth.you_dont_have_an_account')}&nbsp;</span>
                  <Link href={PATH.AUTH_PATH_URL.REGISTER} style={{ color: '#116835' }}>
                    {t('auth.register')}
                  </Link>
                </div>
              </CardContent>
            </Card>
          </TabsContent>
          <TabsContent value="register">
            <Card>
              <CardHeader>
                <CardTitle>{t('auth.register')}</CardTitle>
                <CardDescription>Change your password here. After saving, you&lsquo;ll be logged out.</CardDescription>
              </CardHeader>
              <CardContent className="space-y-2">
                <RegisterComponent />
                <div className="my-3 font-medium">
                  <span>{t('auth.you_already_have_an_account')}&nbsp;</span>
                  <Link href={PATH.AUTH_PATH_URL.LOGIN} style={{ color: '#116835' }}>
                    {t('auth.login')}
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
