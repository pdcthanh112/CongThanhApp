import { useAppSelector } from '@/redux/store';
import { useQuery } from '@tanstack/react-query';
import { getNotificationByCustomer } from 'api/notificationApi';
import Link from 'next/link';
import moment from 'moment';
import { useTranslation } from 'next-i18next';
import Image from 'next/image';
import DefaultImage from '@/assets/images/default-image.jpg';
import { Customer, Notification } from '@/models/types';

export default function NotificationModal() {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const { t } = useTranslation('common');

  const { data: listNotification, isLoading } = useQuery(
    ['listNotification'],
    async () => await getNotificationByCustomer(currentUser.userInfo.accountId).then((response) => response.data),
  );

  if (isLoading) return <>Loading...</>;

  return (
    <div className="text-base">
      <h3 className="font-semibold text-lg pl-3">{t('common.notification')}</h3>
      <div className="text-end">
        <Link href={'/notification'} className="mr-5 hover:underline hover:text-yellow-400">
          {t('common.see_all')}
        </Link>
      </div>
      {listNotification.map((item: Notification) => (
        <div key={item.id} className="px-4 py-2 hover:cursor-pointer hover:bg-slate-200 flex relative">
          <Image src={DefaultImage} alt={''} width={70} height={70} className='rounded-full'/>
          <div className='mx-3'>
            <div className="font-medium">{item.title}</div>
            <div className='text-sm'>{item.content}</div>
            <div className="text-end text-sm absolute right-3 bottom-2 opacity-80 italic">{moment(item.createdDate).fromNow()}</div>
          </div>
        </div>
      ))}
    </div>
  );
}
