import { Cart, Customer } from '@/models/types';
import { useTranslations } from 'next-intl';
import ShowListCart from '@/components/Cart/ShowListCart';

const CartPage = () => {

  const t = useTranslations();

  return (
    <div className="w-full">
      <h3 className="px-5 py-2 mb-3 text-3xl bg-white">{t('cart.your_cart')}</h3>
      <ShowListCart/>
    </div>
  );
};

export default CartPage;
