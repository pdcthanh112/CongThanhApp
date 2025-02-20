import Link from 'next/link';
import { Notification } from '@/models/types';
import { PATH } from '@/utils/constants/path';
import NotificationItem from './NotificationItem';
import NotificationItemSkeleton from './NotificationItemSkeleton';
import { useTranslations } from 'next-intl';
import { useAuthenticated } from '@/hooks/auth/useAuthenticated';

type PropsType = {
  listNotification: Notification[];
  loading: boolean;
};

export default function NotificationModal({ listNotification, loading = true }: PropsType) {
  const {user} = useAuthenticated()
  const t = useTranslations();

  if(!user) return <div>Needed signin</div> 

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
