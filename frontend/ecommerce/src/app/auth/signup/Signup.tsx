import { NextPage } from 'next';
import React, { useState, ReactNode } from 'react';
import styled from 'styled-components';
import { useForm, SubmitHandler } from 'react-hook-form';
import { SignupForm } from '@models/form';
import { Autocomplete, TextField, Icon, Card, Tooltip } from '@mui/material';
import { genderData } from '@utils/constants/dropdownData';
import Button from '@components/UI/Button';
import { Visibility, VisibilityOff, RadioButtonChecked, CheckCircleOutline } from '@mui/icons-material';
import Link from 'next/link';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import { useTranslation } from 'next-i18next';

type InputComponentProps = {
  title: string;
  children: ReactNode;
  error?: any;
  className?: string;
}

const InputComponent: React.FC<InputComponentProps> = (element) => {
  return (
    <div className={`mb-3 h-20 ${element.className}`}>
      <h5 className="font-medium">{element.title}</h5>
      <div className={`${element.error && 'bg-red-100'}`}>{element.children}</div>
      <span className="text-red-500">{element.error}</span>
    </div>
  );
};

const InputField = styled.div`
  border: 1px solid #b6b6b6;
  padding: 0.45rem 1.8rem 0.45rem 0.6rem;
  border-radius: 4px;
  position: relative;
`;

const Signup: NextPage = (): React.ReactElement => {
  const { t } = useTranslation('common');
  const [showPassword, setShowPassword] = useState(false);
  const [showValidatePassword, setShowValidatePassword] = useState(false);

  const [validated, setValidated] = useState({
    lowerValidated: false,
    upperValidated: false,
    numberValidated: false,
    specialValidated: false,
    lengthValidated: false,
  });

  const { register, setValue, watch, handleSubmit, formState, getValues } = useForm<SignupForm>();
  const onSubmit: SubmitHandler<SignupForm> = (data) => {
    // dispatch(login(data)).then((res) => {
    //   if (res.payload.status === 'SUCCESS') {
    //     navigate.push('/home');
    //   }
    // });
    console.log('RRRRRRRRRRRRRRRRRRRR', data);
  };

  const lower = new RegExp(/(?=.*[a-z])/);
  const upper = new RegExp(/(?=.*[A-Z])/);
  const number = new RegExp(/(?=.*[0-9])/);
  const special = new RegExp(/(?=.*[!@#$%^&*+-<>=])/);
  const length = new RegExp(/^(?=.{8,32}$)/);

  const handleChange = (value: string) => {
    setValidated({
      ...validated,
      lowerValidated: lower.test(value),
      upperValidated: upper.test(value),
      numberValidated: number.test(value),
      specialValidated: special.test(value),
      lengthValidated: length.test(value),
    });
  };

  const ValidatePasswordForm = () => {
    return (
      <React.Fragment>
        <div className={`${validated.lengthValidated && 'text-green-500'}`}>
          <Icon component={validated.lengthValidated ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
          <span className="ml-1">{t('signup.8_32_characters')}</span>
        </div>
        <div className={`${validated.lowerValidated && 'text-green-500'}`}>
          <Icon component={validated.lowerValidated ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
          <span className="ml-1">{t('signup.at_least_1_lowercase_letter')}</span>
        </div>
        <div className={`${validated.upperValidated && 'text-green-500'}`}>
          <Icon component={validated.upperValidated ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
          <span className="ml-1">{t('signup.at_least_1_uppercase_letter')}</span>
        </div>
        <div className={`${validated.numberValidated && 'text-green-500'}`}>
          <Icon component={validated.numberValidated ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
          <span className="ml-1">{t('signup.at_least_1_numberic_character')}</span>
        </div>
        <div className={`${validated.specialValidated && 'text-green-500'}`}>
          <Icon component={validated.specialValidated ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
          <span className="ml-1">{t('signup.at_least_1_special_character')}</span>
        </div>
      </React.Fragment>
    );
  };
  
  return (
    <Card className="flex items-center justify-center h-[85vh] w-screen bg-gradient-to-tr from-blue-500 to-purple-500">
      <div className="bg-white h-[80%] w-[52%] px-8 py-3">
        <h2 className="flex justify-center text-2xl font-semibold font-mono">{t('signup.register')}</h2>
        <div className="flex justify-end font-mono">
          <span>{t('signup.you_already_have_an_account')}</span>
          <Link href="/auth/login" className="hover:text-yellow-600 hover:cursor-pointer hover:underline">
            {t('common.login')}
          </Link>
        </div>
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="grid grid-cols-2 gap-10">
            <InputComponent title="Email" error={formState.errors.email?.message}>
              <InputField>
                <input
                  type="email"
                  {...register('email', {
                    required: t('common.field_is_require', { field: 'Email' }),
                  })}
                  placeholder={t('common.enter_your_field', { field: 'email', label: 'email' })}
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.email && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>

            <InputComponent title={t('signup.name')} error={formState.errors.name?.message}>
              <InputField>
                <input
                  type="text"
                  {...register('name', {
                    required: 'Name is require',
                  })}
                  placeholder="Enter your fullname"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.name && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>
          </div>

          <div className="grid grid-cols-2 gap-10">
            <InputComponent title={t('signup.password')} error={formState.errors.password?.message}>
              <InputField>
                <Tooltip title={<ValidatePasswordForm />} placement="top" open={showValidatePassword}>
                  <input
                    type={showPassword ? 'text' : 'password'}
                    {...register('password', {
                      required: 'Password is require',
                      pattern: { value: /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,32}/, message: 'Password not valid' },
                      minLength: { value: 8, message: 'Password must be 8 - 32 digit' },
                      maxLength: { value: 32, message: 'Password must be 8 - 32 digit' },
                    })}
                    placeholder="Enter your password"
                    className={`focus:outline-none ml-3 w-[100%] ${formState.errors.password && 'bg-red-100'}`}
                    onChange={(e) => handleChange(e.target.value)}
                    onFocus={() => setShowValidatePassword(true)}
                    onBlur={() => setShowValidatePassword(false)}
                  />
                </Tooltip>
                <Icon
                  component={showPassword ? Visibility : VisibilityOff}
                  titleAccess={showPassword ? t('common.hide_password') : t('common.show_password')}
                  fontSize="small"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute top-2 right-3"
                />
              </InputField>
            </InputComponent>

            <InputComponent title={t('signup.confirm')} error={formState.errors.confirm?.message}>
              <InputField>
                <input
                  type={showPassword ? 'text' : 'password'}
                  {...register('confirm', {
                    required: 'Confirm password is require',
                    validate: (value: string) => value === watch('password') || 'Confirm do not match',
                  })}
                  placeholder="Enter your confirm"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.confirm && 'bg-red-100'}`}
                />
                <Icon
                  component={showPassword ? Visibility : VisibilityOff}
                  titleAccess={showPassword ? t('common.hide_password') : t('common.show_password')}
                  fontSize="small"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute top-2 right-3"
                />
              </InputField>
            </InputComponent>
          </div>

          <div className="grid grid-cols-11 gap-10">
            <InputComponent title={t('signup.phone')} className="col-span-4" error={formState.errors.phone?.message}>
              <InputField>
                <input
                  type="text"
                  {...register('phone', {
                    required: 'Phone is require',
                  })}
                  placeholder="Enter your phone number"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.phone && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>

            <InputComponent title={t('signup.gender')} className="col-span-3" error={formState.errors.gender?.message}>
              <Autocomplete
                options={genderData()}
                size={'small'}
                // sx={{ width: 220, marginRight: 2 }}
                getOptionLabel={(option) => option.title}
                renderInput={(params) => <TextField {...params} label="Gender" />}
                onInputChange={(event, value) => {
                  setValue('gender', value);
                }}
                // {...register('gender')}
              />
            </InputComponent>
            <InputComponent title={t('signup.date_of_birth')} className="col-span-4" error={formState.errors.gender?.message}>
              <InputField>
                <input
                  type="date"
                  {...register('dob')}
                  // placeholder="Enter your phone number"
                  className={`focus:outline-none ml-3 w-[100%] ${formState.errors.dob && 'bg-red-100'}`}
                />
              </InputField>
            </InputComponent>
          </div>
          <InputComponent title={t('signup.address')} error={formState.errors.address?.message}>
            <InputField>
              <input
                type="text"
                {...register('address', {
                  required: 'Address is require',
                })}
                placeholder="Enter your address"
                className={`focus:outline-none ml-3 w-[100%] ${formState.errors.address && 'bg-red-100'}`}
              />
            </InputField>
          </InputComponent>

          <div className="flex justify-center">
            <Button className="bg-yellow-300 rounded-lg w-[40%]">{t('signup.register')}</Button>
          </div>
        </form>
      </div>
    </Card>
  );
};

export default Signup;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}
