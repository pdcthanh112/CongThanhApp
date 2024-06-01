import { NextPage } from 'next';
import { useQuery } from '@tanstack/react-query';
import { getCartByCustomerId } from 'api/cartApi';
import CartItem from './CartItem';
import { Card, Icon } from '@mui/material';
import { Delete as DeleteIcon } from '@mui/icons-material';
import Button from '@components/UI/Button';
import { useAppSelector } from '@redux/store';
import { useRouter } from 'next/router';
import { Cart, Customer } from '@models/type';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import { useTranslation } from 'next-i18next';
import CartEmptyImage from '@assets/images/cart-empty-image.png';
import Image from 'next/image';
import { Popconfirm } from 'antd';
import { useDeleteCart } from '@hooks/cart/cartHook';
import { toast } from 'react-toastify';

const Cart: NextPage = (): React.ReactElement => {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const router = useRouter();
  const { t } = useTranslation('common');

  const { mutate: deleteCart } = useDeleteCart();

  const { data: listCart, isLoading } = useQuery(['cart'], async () => await getCartByCustomerId(currentUser.userInfo.accountId).then((response) => response.data));

  const handleDeleteCart = (cartId: string) => {
    deleteCart(cartId, {
      onSuccess() {
        toast.success(t('cart.delete_cart_successfully'));
      },
      onError(error) {
        toast.error(t('cart.delete_cart_failed'));
        console.log(error);
      },
    });
  };

  if (!listCart) {
    return (
      <div className="w-full h-[40rem] bg-white text-center">
        <div className="w-full pt-12 flex justify-center">
          <Image src={CartEmptyImage} alt={'Cart Empty'} width={300} height={100} />
        </div>
        <div className="w-full flex justify-center font-medium text-xl mb-5">{t('cart.cart_empty')}</div>
        <div className="w-full flex justify-center">
          <Button className="bg-green-400 rounded-xl text-white" onClick={() => router.push('/')}>
            {t('common.back_to_home')}
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="w-full">
      <h3 className="px-5 py-2 mb-3 text-3xl bg-white">{t('cart.your_cart')}</h3>
      <div className="flex flex-col w-[90%] mx-auto">
        {listCart.map((cart: Cart) => {
          let countItem: number = 0;
          let sumPrice: number = 0;
          return (
            <div key={cart.id} className="md:flex mb-5 ">
              <Card className="md:w-[80%] min-h-[10vh] px-4 py-5">
                <div className="flex justify-between">
                  <div className="font-medium text-lg">{cart.name}</div>
                  <Popconfirm
                    title="Are you sure to remove this item from wishlist?"
                    okText={t('common.yes')}
                    okButtonProps={{ style: { backgroundColor: '#1677ff' } }}
                    cancelText={t('common.no')}
                    onConfirm={() => {
                      handleDeleteCart(cart.id);
                    }}
                    placement="topRight">
                    <Icon titleAccess={t('common.remove_this_item')} component={DeleteIcon} className="hover:cursor-pointer opacity-50 hover:opacity-100" />
                  </Popconfirm>
                </div>
                {cart.cartItems?.length > 0 ? (
                  <>
                    {cart.cartItems?.map((item: CartItem) => {
                      countItem++;
                      sumPrice += item.product.price * item.quantity;
                      return <CartItem key={item.id} item={item} />;
                    })}
                  </>
                ) : (
                  <div className="flex justify-center">{t('cart.this_cart_have_no_item')}</div>
                )}
              </Card>
              <Card className="md:w-[20%] ml-4 p-4 relative">
                <h1 className="font-semibold text-xl">{t('common.checkout')}</h1>
                {cart.cartItems?.length > 0 && (
                  <>
                    <div className="px-3">
                      <div className="flex justify-between">
                        <span>Items({countItem}):</span>
                        <span>{sumPrice.toFixed(2)}</span>
                      </div>
                      <div className="flex justify-between">
                        {t('common.shipping')}: <span>{t('common.free')}</span>
                      </div>
                    </div>
                    <div className="flex justify-between px-3 absolute bottom-16 border-t-2 border-t-gray-400 w-[85%]">
                      <span>{t('cart.subtotal')}:</span>
                      <span>{sumPrice.toFixed(2)}</span>
                    </div>
                    <Button className="absolute bottom-4 w-[88%] bg-yellow-400" onClick={() => router.push(`/checkout/${cart.id}`)}>
                      {t('common.checkout')}
                    </Button>
                  </>
                )}
              </Card>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Cart;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}
