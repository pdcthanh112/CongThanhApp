import React from 'react';
import Image from 'next/image';
import { CartItem } from '@/models/types';
import { useQuery } from '@tanstack/react-query';
import { getDefaultImageByProductId } from '@/api/productApi';
import { useRouter } from 'next/navigation';
import { IMAGE } from '@/utils/constants/path';
import QuantitySelector from '@/components/QuantitySelector';
import { formatCurrency } from '@/utils/helper';

type CartItemPropsType = {
  item: CartItem;
};

const ShowCartItem = ({ item }: CartItemPropsType) => {
  const router = useRouter();
  const { data: productImage } = useQuery({
    queryKey: ['product-default-image', item],
    queryFn: async () => await getDefaultImageByProductId(item.product.id).then((response) => response.data),
  });

  return (
    <div
      key={item.id}
      className="flex justify-between px-5 py-2 hover:bg-gray-100 hover:cursor-pointer"
      title={item.product.name}
      onClick={() => router.push(`product/${item.product.id}`)}
    >
      <span className="flex items-center">
        <Image
          src={productImage?.imagePath || IMAGE.defaultImage}
          alt={productImage?.alt || ''}
          width={40}
          height={40}
          className="border border-gray-300"
        />
        <h4 className="truncate ml-2 w-56 text-sm">{item.product.name}</h4>
        <span>{formatCurrency(120000)}</span>
        <QuantitySelector value={item.quantity}/>
      </span>
    </div>
  );
};

export default ShowCartItem;
