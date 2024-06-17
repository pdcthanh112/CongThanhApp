'use client';
import React from 'react';
import { Cart, Customer } from '@/models/types';
import { useAppSelector } from '@/redux/store';
import { useTranslations } from 'next-intl';
import { useQuery } from '@tanstack/react-query';
import { CART_KEY } from '@/utils/constants/queryKey';
import { getCartByCustomerId } from '@/api/cartApi';
import ListCart from '../ListCart';

const ShowListCart = () => {
  // const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);

  const t = useTranslations();

  const { data: listCart, isLoading } = useQuery({
    queryKey: [CART_KEY, 1],
    queryFn: async () =>
      await getCartByCustomerId('e6169aa0-d4a5-4740-b510-9285bbb73a0e').then((response) => response.data),
  });

  return (
    <React.Fragment>
      <div className="w-4/5 mx-auto">
        <ListCart data={listCart} loading={isLoading} />
      </div>
    </React.Fragment>
  );
};

export default ShowListCart;
