import React from 'react';
import Image from 'next/image';
import { CartItem, CartItemDetail } from '@/models/types';
import { useQuery } from '@tanstack/react-query';
import { IMAGE } from '@/utils/constants/path';
import QuantitySelector from '@/components/quantity-selector';
import { formatCurrency } from '@/utils/helper';
import { getItemDetail } from '@/api/cartApi';
import Link from 'next/link';
import { Icon } from '@mui/material';
import { Delete } from '@mui/icons-material';
import { useTranslations } from 'next-intl';

type PropsType = {
  cartId: number;
  item: CartItem;
};

export default function ShowCartItem({ cartId, item }: PropsType) {
  const t = useTranslations();

  const { data: itemDetail, isLoading } = useQuery<CartItemDetail>({
    queryKey: ['cart-item-detail', item],
    queryFn: async () => await getItemDetail({ cartId: cartId, itemId: item.id }).then((response) => response.data),
  });

  const handleIncrease = (value: number) => {
    console.log('IIIIIIIIIIIIIIIIIIII', value);
    // console.log('Deeeeeeeeeeee', useDebounce(value))
  };
  const handleDecrease = (value: number) => {
    console.log('DDDDDDDDDDDDDDDDDDDĐ', value);
  };

  if (isLoading || !itemDetail) return <div>Loading...</div>;

  return (
    <div
      key={itemDetail.id}
      className="justify-between items-center w-full grid grid-cols-12 gap-3"
      title={itemDetail.product.name}
    >
      <span className="w-24 h-24 relative col-span-2">
        <Image
          src={itemDetail.product.image || IMAGE.defaultImage}
          alt={itemDetail.product.name}
          objectFit="fit"
          fill
          className="border border-gray-300"
        />
      </span>
      <Link href={itemDetail.product.slug} className="truncate col-span-5">
        {itemDetail.product.name}
      </Link>
      <span className="col-span-2">{formatCurrency(120000)}</span>
      <div className="col-span-2">
        <QuantitySelector
          value={item.quantity}
          onIncrease={(value) => handleIncrease(value)}
          onDecrease={(value) => handleDecrease(value)}
        />
      </div>
      <span className="col-span-1 flex justify-end">
        <Icon
          titleAccess={t('common.remove_this_item')}
          component={Delete}
          className="hover:cursor-pointer opacity-50 hover:opacity-100"
        />
      </span>
    </div>
  );
}
