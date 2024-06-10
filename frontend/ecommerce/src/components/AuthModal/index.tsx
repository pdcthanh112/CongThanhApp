'use client'
import { useState, ReactNode } from 'react';
import './style.scss';
import Image from 'next/image';
import Link from 'next/link';
import { Modal, Box, Icon } from '@mui/material';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useAppDispatch } from '@/redux/store';
import { Visibility, VisibilityOff, Close, Email as EmailIcon, Lock as LockIcon, AccountCircle } from '@mui/icons-material';
import { loginRequested } from '@/redux/actions/auth';
import AppLogo from '@/assets/images/app-logo.png';
import { closeModalAuth } from '@/redux/features/modalAuth';
import { useTranslations } from 'next-intl';
import { getAuthLogo } from '@/utils/helper';
import { signIn } from 'next-auth/react';
import { LoginForm, SignupForm } from '@/models/form';
import { Button } from '../ui/button';

interface InputComponentProps {
  title: string;
  children: ReactNode;
  error?: any;
  className?: string;
}

const InputComponent: React.FC<InputComponentProps> = (element) => {
  return (
    <div className={`h-20 ${element.className}`}>
      <h5 className="font-medium">{element.title}</h5>
      <div className={`${element.error ? 'bg-red-100' : ''}`}>{element.children}</div>
      <span className="text-red-500">{element.error}</span>
    </div>
  );
};

const AuthModal = () => {
  const dispatch = useAppDispatch();
  const t = useTranslations('common');
  const providers = [
    { id: 'google', name: 'Google' },
    { id: 'facebook', name: 'Facebook' },
    { id: 'twitter', name: 'Twitter' },
    { id: 'apple', name: 'Apple' },
  ];

  const [openAuthModal, setOpenAuthModal] = useState(true);
  const [activeTab, setActiveTab] = useState('login');
  const [showPassword, setShowPassword] = useState(false);

  const formLogin = useForm<LoginForm>();
  const formSignup = useForm<SignupForm>();

  const handleTabChange = (tab: string) => {
    setActiveTab(tab);
  };

  const styleAuthModal = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 500,
    bgcolor: '#f1f2f6',
    boxShadow: 24,
  };

  const onLogin: SubmitHandler<LoginForm> = (data) => {
    dispatch(loginRequested(data));
  };
  const onSignup: SubmitHandler<SignupForm> = (data) => {
    // dispatch(login(data)).then((res) => {
    //   if (res.payload.status === 'SUCCESS') {
    //     navigate.push('/home');
    //   }
    // });
    console.log('RRRRRRRRRRRRRRRRRRRR', data);
  };

  return (
    <Modal
      open={openAuthModal}
      onClose={() => {
        setOpenAuthModal(false);
        dispatch(closeModalAuth());
      }}>
      <Box sx={styleAuthModal}>
        <div className="modal-auth-container">
          <input type="radio" className="tabs__radio" name="auth-tabs" id="tab1" checked={activeTab === 'login'} onChange={() => handleTabChange('login')} />
          <label htmlFor="tab1" className="tabs__label ml-16">
            {t('common.login')}
          </label>
          <div className="tabs__content">
            <div className="flex justify-center">
              <Image src={AppLogo} alt="" width={280} />
            </div>
            <form onSubmit={formLogin.handleSubmit(onLogin)}>
              <InputComponent title="Email" error={formLogin.formState.errors.email?.message}>
                <div>
                  <Icon component={EmailIcon} />
                  <input
                    type="email"
                    {...formLogin.register('email', {
                      required: 'Email is require',
                    })}
                    placeholder="Enter your email"
                    className={`focus:outline-none ml-3 w-[100%] ${formLogin.formState.errors.email ? 'bg-red-100' : ''}`}
                  />
                </div>
              </InputComponent>

              <InputComponent title="Password" error={formLogin.formState.errors.password?.message}>
                <div>
                  <Icon component={LockIcon} />
                  <input
                    type={showPassword ? 'text' : 'password'}
                    {...formLogin.register('password', {
                      required: 'Password is require',
                    })}
                    placeholder="Enter your password"
                    className={`focus:outline-none ml-3 w-[100%] ${formLogin.formState.errors.password ? 'bg-red-100' : ''}`}
                  />
                  <Icon component={showPassword ? Visibility : VisibilityOff} fontSize="small" onClick={() => setShowPassword(!showPassword)} className="absolute top-2 right-3" />
                </div>
              </InputComponent>

              {/* {loginError && <div className="input-error p-2 rounded">Tên đăng nhập hoặc mật khẩu không chính xác</div>} */}

              <Link href="/auth/forget-password" className="flex justify-end hover:underline" title="Forgot password">
                {t('login.forgot_password')}
              </Link>

              <div className="mt-4">
                <Button className="bg-yellow-400 w-full text-white text-lg font-medium">Login</Button>
                {/* {isLoadingAuth && <ReactLoading className="ml-2" type="spin" color="#FF4444" width={37} />} */}
              </div>
            </form>

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
          </div>

          <input type="radio" className="tabs__radio" name="auth-tabs" id="tab2" checked={activeTab === 'signup'} onChange={() => handleTabChange('signup')} />
          <label htmlFor="tab2" className="tabs__label">
            {t('common.signup')}
          </label>
          <div className="tabs__content">
            <form onSubmit={formSignup.handleSubmit(onSignup)}>
              <InputComponent title="Email" error={formSignup.formState.errors.email?.message}>
                <div>
                  <Icon component={EmailIcon} />
                  <input
                    type="email"
                    {...formSignup.register('email', {
                      required: 'Email is require',
                    })}
                    placeholder="Enter your email"
                    className={`focus:outline-none ml-3 w-[100%] ${formSignup.formState.errors.email ? 'bg-red-100' : ''}`}
                  />
                </div>
              </InputComponent>

              <InputComponent title="Name" error={formSignup.formState.errors.name?.message}>
                <div>
                  <Icon component={AccountCircle} />
                  <input
                    type="text"
                    {...formSignup.register('name', {
                      required: 'Name is require',
                    })}
                    placeholder="Enter your fullname"
                    className={`focus:outline-none ml-3 w-[100%] ${formSignup.formState.errors.name ? 'bg-red-100' : ''}`}
                  />
                </div>
              </InputComponent>

              <InputComponent title="Password" error={formSignup.formState.errors.password?.message}>
                <div>
                  <Icon component={LockIcon} />
                  <input
                    type={showPassword ? 'text' : 'password'}
                    {...formSignup.register('password', {
                      required: 'Password is require',
                      minLength: { value: 8, message: 'Password must be 8 - 32 digit' },
                      maxLength: { value: 32, message: 'Password must be 8 - 32 digit' },
                    })}
                    placeholder="Enter your password"
                    className={`focus:outline-none ml-3 w-[100%] ${formSignup.formState.errors.password ? 'bg-red-100' : ''}`}
                  />
                  <Icon component={showPassword ? Visibility : VisibilityOff} fontSize="small" onClick={() => setShowPassword(!showPassword)} className="absolute top-2 right-3" />
                </div>
              </InputComponent>

              <InputComponent title="Confirm" error={formSignup.formState.errors.confirm?.message}>
                <div>
                  <Icon component={LockIcon} />
                  <input
                    type={showPassword ? 'text' : 'password'}
                    {...formSignup.register('confirm', {
                      required: 'Confirm password is require',
                      validate: (value) => value === formSignup.watch('password') || 'Confirm do not match',
                    })}
                    placeholder="Enter your confirm"
                    className={`focus:outline-none ml-3 w-[100%] ${formSignup.formState.errors.confirm ? 'bg-red-100' : ''}`}
                  />
                  <Icon component={showPassword ? Visibility : VisibilityOff} fontSize="small" onClick={() => setShowPassword(!showPassword)} className="absolute top-2 right-3" />
                </div>
              </InputComponent>

              <Button className="bg-yellow-400 w-full text-white text-lg font-medium">Signup</Button>
              {/* {isLoadingAuth && <ReactLoading className="ml-2" type="spin" color="#FF4444" width={37} />} */}
            </form>
            <div className="my-3 font-medium">
              <span>{t('signup.you_already_have_an_account')}&nbsp;</span>
              <Link href="/auth/login" style={{ color: '#116835' }}>
                {t('common.login')}
              </Link>
            </div>
          </div>

          <Icon
            component={Close}
            titleAccess="Close"
            className="absolute top-2 right-2 hover:cursor-pointer"
            onClick={() => {
              setOpenAuthModal(false);
              dispatch(closeModalAuth());
            }}
          />
        </div>
      </Box>
    </Modal>
  );
};

export default AuthModal;
