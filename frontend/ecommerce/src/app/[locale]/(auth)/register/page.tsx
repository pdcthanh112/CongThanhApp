import React from 'react'
import Image from 'next/image';
import LoginImage from '@assets/images/login-page-image.png'
import RegisterForm from './RegisterForm';

const RegisterPage = () => {

  return (
    <div className='grid grid-cols-12 h-full'>
      <div className={`col-span-6 relative`}>
        <Image src={LoginImage} alt={""} objectFit="fit" fill/>
      </div>
      <div className={`col-span-6`}>
        <RegisterForm />
      </div>
    </div>
  )
}

export default RegisterPage