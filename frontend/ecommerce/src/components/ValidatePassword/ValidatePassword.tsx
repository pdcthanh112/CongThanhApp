import React from 'react';
import { Ban, CheckCheck } from 'lucide-react';
import { Icon } from '@mui/material';
import {lengthRegex, lowerCharacterRegex, numberCharacterRegex, specialCharacterRegex, upperCharacterRegex} from '@/utils/regex';
import { useTranslations } from 'next-intl';

type PropsType = {
  password: string;
};

export default function ValidatePassword({ password }: PropsType) {
  const t = useTranslations('common');

  return (
    <div className='text-black'>
      <div className={`${lengthRegex.test(password) && 'text-green-500'}`}>
        <Icon 
          component={lengthRegex.test(password) ? CheckCheck : Ban} 
          fontSize="inherit" 
        />
        <span className="ml-1">{t('signup.8_32_characters')}</span>
      </div>
      <div className={`${lowerCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon
          component={lowerCharacterRegex.test(password) ? CheckCheck : Ban}
          fontSize="inherit"
        />
        <span className="ml-1">{t('signup.at_least_1_lowercase_letter')}</span>
      </div>
      <div className={`${upperCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon
          component={upperCharacterRegex.test(password) ? CheckCheck : Ban}
          fontSize="inherit"
        />
        <span className="ml-1">{t('signup.at_least_1_uppercase_letter')}</span>
      </div>
      <div className={`${numberCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon
          component={numberCharacterRegex.test(password) ? CheckCheck : Ban}
          fontSize="inherit"
        />
        <span className="ml-1">{t('signup.at_least_1_numberic_character')}</span>
      </div>
      <div className={`${specialCharacterRegex.test(password) && 'text-green-500'}`}>
        <Icon
          component={specialCharacterRegex.test(password) ? CheckCheck : Ban}
          fontSize="inherit"
        />
        <span className="ml-1">{t('signup.at_least_1_special_character')}</span>
      </div>
    </div>
  );
}
