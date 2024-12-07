import { useAppSelector } from '@/redux/store';
import Link from 'next/link';
import { Customer, Notification } from '@/models/types';
import { PATH } from '@/utils/constants/path';
import NotificationItem from './NotificationItem';
import NotificationItemSkeleton from './NotificationItemSkeleton';
import { useTranslations } from 'next-intl';

type PropsType = {
  listNotification: Notification[];
  loading: boolean;
};

export default function NotificationModal({ listNotification, loading = true }: PropsType) {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const t = useTranslations();

  if(!currentUser) return <div>Needed signin</div> 

  return (
    <div>
      <h3 className="font-semibold text-lg pl-3">{t('common.notification')}</h3>
      <div className="text-end">
        <Link href={PATH.NOTIFICATION_PATH_URL.NOTIFICATION} className="mr-5 hover:underline hover:text-yellow-400">
          {t('common.see_all')}
        </Link>
      </div>
      {loading ? (
        Array.from(Array(4)).map((_, id) => (
          <div key={id} className="px-4 py-2 w-full">
            <NotificationItemSkeleton />
          </div>
        ))
      ) : listNotification.length > 0 ? (
        listNotification?.map((item: Notification) => <NotificationItem key={item.id} item={item} />)
      ) : (
        <div>you have no noti</div>
      )}
    </div>
  );
}
