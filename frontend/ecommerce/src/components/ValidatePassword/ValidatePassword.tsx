import React from 'react';
import { CheckCircleOutline, RadioButtonChecked } from '@mui/icons-material';
import { Icon } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { lengthRegex, lowerCharacterRegex, numberCharacterRegex, specialCharacterRegex, upperCharacterRegex } from '@utils/regex';

type PropsType = {
  password: string;
  // setPassword: (value: string) => void;
};

const ValidatePassword = ({ password }: PropsType) => {

  const t = useTranslations('common');

  return (
    <React.Fragment>
      <div className={`${lengthRegex.test(password) && 'text-green-500'}`}>
        <Icon component={lengthRegex.test(password) ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
        <span className="ml-1">{t('signup.8_32_characters')}</span>
      </div>
      <div className={`${lowerCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon component={lowerCharacterRegex.test(password) ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
        <span className="ml-1">{t('signup.at_least_1_lowercase_letter')}</span>
      </div>
      <div className={`${upperCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon component={upperCharacterRegex.test(password) ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
        <span className="ml-1">{t('signup.at_least_1_uppercase_letter')}</span>
      </div>
      <div className={`${numberCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon component={numberCharacterRegex.test(password) ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
        <span className="ml-1">{t('signup.at_least_1_numberic_character')}</span>
      </div>
      <div className={`${specialCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon component={specialCharacterRegex.test(password) ? CheckCircleOutline : RadioButtonChecked} fontSize="inherit" />
        <span className="ml-1">{t('signup.at_least_1_special_character')}</span>
      </div>
    </React.Fragment>
  );
};

export default ValidatePassword;
