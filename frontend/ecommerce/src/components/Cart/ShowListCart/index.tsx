'use client';
import React from 'react';
import { Cart, CartItem } from '@/models/types';
import { Card, Icon } from '@mui/material';
import { Popconfirm } from 'antd';
import { useDeleteCart } from '@/hooks/cart/cartHook';
import { toast } from 'react-toastify';
import { useTranslations } from 'next-intl';
import { Delete } from '@mui/icons-material';
import { Button, Checkbox, Separator } from '@/components/ui';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import { IMAGE, PATH } from '@/utils/constants/path';
import Link from 'next/link';
import ShowCartItem from './ShowCartItem';

type PropsType = {
  data: Cart[];
  loading: boolean;
};

export default function ShowListCart({ data }: PropsType) {
  const router = useRouter();
  const t = useTranslations();

  const { mutate: deleteCart } = useDeleteCart();

  const handleDeleteCart = (cartId: number) => {
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

  let totalCountItem: number = 0;
  let totalSumPrice: number = 0;

  return (
    <React.Fragment>
      {data.map((cart: Cart) => {
        let countItem: number = 0;
        let sumPrice: number = 0;
        return (
          <div key={cart.id} className="md:flex mb-5 ">
            <Card className="md:w-[80%] min-h-[10vh] px-4 py-5">
              <div className="flex justify-between">
                <div className="font-medium text-lg">{cart.cartItems.length > 0 && <Checkbox/>} {cart.name}</div>
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
                    totalCountItem++;
                    // sumPrice += item.product.price * item.quantity;
                    return <ShowCartItem key={item.id} cartId={cart.id} item={item} />;
                  })}
                </>
              ) : (
                <div className="flex justify-center">{t('cart.this_cart_have_no_item')}</div>
              )}
            </Card>
            <Card className="md:w-[20%] ml-4 p-4 relative">
              <span className="font-semibold text-xl">{t('common.checkout')}</span>
              {cart.cartItems?.length > 0 && (
                <React.Fragment>
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
                </React.Fragment>
              )}
            </Card>
          </div>
        );
      })}
      <div className="flex justify-end">
        <Card className="w-[22rem] px-4 py-5">
          <h3 className="font-semibold text-xl text-center mb-3">{t('common.checkout')} all product</h3>

          <div className="px-3">
            <div className="flex justify-between">
              <span>Items({totalCountItem}):</span>
              <span>{totalSumPrice.toFixed(2)}</span>
            </div>
            <div className="flex justify-between">
              {t('common.shipping')}: <span>{t('common.free')}</span>
            </div>
          </div>
          <Separator className='h-[2px] bg-black'/>
          <div className="flex justify-between px-3 bottom-16">
            <span>{t('cart.subtotal')}:</span>
            <span>{totalSumPrice.toFixed(2)}</span>
          </div>
          
          <Button className="w-full bg-yellow-400" onClick={() => router.push(`/checkout/${cart.id}`)}>
            {t('common.checkout')}
          </Button>
        </Card>
      </div>
    </React.Fragment>
  );
}
