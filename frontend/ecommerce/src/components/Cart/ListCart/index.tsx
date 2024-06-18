'use client';
import React from 'react';
import { Cart, CartItem } from '@/models/types';
import { Card, Icon } from '@mui/material';
import { Popconfirm } from 'antd';
import { useDeleteCart } from '@/hooks/cart/cartHook';
import { toast } from 'react-toastify';
import { useTranslations } from 'next-intl';
import { Delete } from '@mui/icons-material';
import { Button } from '@/components/ui';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import { IMAGE, PATH } from '@/utils/constants/path';
import Link from 'next/link';
import ShowCartItem from './ShowCartItem';

type ListCartPropsType = {
  data: Cart[];
  loading: boolean;
};

const ListCart = ({ data, loading = true }: ListCartPropsType) => {
  const router = useRouter();
  const t = useTranslations();

  const { mutate: deleteCart } = useDeleteCart();

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

  if (!data) {
    return (
      <div className="w-full h-[40rem] bg-white text-center">
        <div className="w-full pt-12 flex justify-center">
          <Image src={IMAGE.cartEmpty} alt={'Cart Empty'} width={300} height={100} />
        </div>
        <div className="w-full flex justify-center font-medium text-xl mb-5">{t('cart.cart_empty')}</div>
        <div className="w-full flex justify-center">
          <Link href={PATH.HOME}>
            <Button className="bg-green-400 rounded-xl text-white">{t('common.back_to_home')}</Button>
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div>
      {data.map((cart: Cart) => {
        let countItem: number = 0;
        let sumPrice: number = 0;
        return (
          <div key={cart.id} className="md:flex mb-5 ">
            <Card className="md:w-[80%] min-h-[10vh] px-4 py-5">
              <div className="flex justify-between">
                <div className="font-medium text-lg">{cart.name}</div>
                <Popconfirm
                  title="Are you sure to delete this cart?"
                  okText={t('common.yes')}
                  okButtonProps={{ style: { backgroundColor: '#1677ff' } }}
                  cancelText={t('common.no')}
                  onConfirm={() => {
                    handleDeleteCart(cart.id);
                  }}
                  placement="topRight"
                >
                  <Icon
                    titleAccess={t('common.remove_this_item')}
                    component={Delete}
                    className="hover:cursor-pointer opacity-50 hover:opacity-100"
                  />
                </Popconfirm>
              </div>
              {cart.cartItems?.length > 0 ? (
                <>
                  {cart.cartItems?.map((item: CartItem) => {
                    countItem++;
                    sumPrice += item.product.price * item.quantity;
                    return <ShowCartItem key={item.id} item={item} />;
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

                  <Button
                    className="absolute bottom-4 w-[88%] bg-yellow-400"
                    onClick={() => router.push(`/checkout/${cart.id}`)}
                  >
                    {t('common.checkout')}
                  </Button>
                </>
              )}
            </Card>
          </div>
        );
      })}
    </div>
  );
};

export default ListCart;
