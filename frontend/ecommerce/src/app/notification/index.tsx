import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import React from 'react'

const Notification = () => {
  return (
    <div>Notification</div>
  )
}

export default Notification;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}