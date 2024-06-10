import Image from 'next/image';
import DefaultImage from '@assets/images/default-image.jpg';
import { Card, Rating, Icon } from '@mui/material';
import Button from '@components/UI/Button';
import { useRouter } from 'next/router';
import { roundNumber } from '@utils/helper';
import { ShoppingCart, Source } from '@mui/icons-material';
import { useAppDispatch, useAppSelector } from '@redux/store';
import { useTranslations } from 'next-intl';
import { useQuery } from '@tanstack/react-query';
import { openModalAuth } from '@redux/features/modalAuth';
import { toast } from 'react-toastify';
import { HeartEmpty, HeartFull } from '@assets/icons';
import { useAddProductToWishlist, useRemoveProductFromWishlist } from '@hooks/wishlist/wishlistHook';
import { getWishlistByCustomer } from 'api/wishlistApi';
import { useEffect, useState } from 'react';
import { getRatingStarofProduct } from 'api/reviewApi';
import { getDefaultImageByProductId, getSoldByProduct } from 'api/productApi';
import { Customer, Product, ProductImage, Wishlist } from '@models/type';

interface ProductProps {
  product: Product;
}

const ProductItemCard = ({ product }: ProductProps) => {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const router = useRouter();
  const t = useTranslations('common');
  const dispatch = useAppDispatch();

  const [ratingStar, setRatingStar] = useState<{ vote: number; value: number }>({ vote: 0, value: 0.0 });
  const [sold, setSold] = useState<number>(0);
  const [productDefaultImage, setProductDefaultImage] = useState<ProductImage>();

  const { data: wishlist } = useQuery(['wishlist'], async () => await getWishlistByCustomer(currentUser.userInfo.accountId).then((response) => response.data));

  const { mutate: addProductToWishlist } = useAddProductToWishlist();
  const { mutate: removeProductFromWishlist } = useRemoveProductFromWishlist();

  useEffect(() => {
    const fetchData = async () => {
      await getDefaultImageByProductId(product.id).then((response) => {
        if (response && response.data) {
          setProductDefaultImage(response.data);
        }
      });
    };
    fetchData();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      await getRatingStarofProduct(product.id).then((response) => {
        if (response && response.data) {
          setRatingStar(response.data);
        }
      });
    };
    fetchData();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      await getSoldByProduct(product.id).then((response) => {
        if (response && response.data) {
          setSold(response.data);
        }
      });
    };
    fetchData();
  }, []);

  const handleAddToCart = (productId: string) => {
    if (currentUser) {
      //  setshowSelectCart(true)
      // addToCart('CART_ID', 1, productId);
    } else {
      dispatch(openModalAuth());
    }
  };

  const handleAddToWishlist = (productId: string) => {
    if (currentUser) {
      try {
        addProductToWishlist(
          { customerId: currentUser.userInfo.accountId, productId: productId },
          {
            onSuccess() {
              toast.success(t('wishlist.add_item_to_wishlist_successfully'));
            },
            onError(error) {
              toast.error(t('wishlist.add_item_to_wishlist_failed'));
              console.log(error);
            },
          },
        );
      } catch (error) {
        toast.error(t('wishlist.add_item_to_wishlist_failed'));
      }
    } else {
      dispatch(openModalAuth());
    }
  };

  const handleRemoveFromWishlist = async (productId: string) => {
    if (currentUser) {
      try {
        removeProductFromWishlist(
          { customerId: currentUser.userInfo.accountId, productId: productId },
          {
            onSuccess() {
              toast.success(t('wishlist.remove_item_from_wishlist_successfully'));
            },
            onError(error) {
              toast.error(t('wishlist.remove_item_from_wishlist_failed'));
              console.log(error);
            },
          },
        );
      } catch (error) {
        toast.error(t('wishlist.remove_item_from_wishlist_failed'));
      }
    } else {
      dispatch(openModalAuth());
    }
  };

  return (
    <Card key={product.id} className=" bg-white z-30 p-3 text-sm hover:cursor-pointer">
      <div className="w-full flex items-center justify-center relative group">
        <div className="h-80">
          <Image src={productDefaultImage?.imagePath || DefaultImage} alt="Product image" width={220} height={400} className='object-fit'/>
        </div>
        <ul className="w-full h-36 bg-gray-100 absolute -bottom-36 flex flex-col items-end justify-center gap-2 font-semibold px-2 border-l border-r group-hover:bottom-0 duration-700">
          <li
            className="text-gray-600 hover:text-black text-sm font-medium border-b-[1px] border-b-gray-400 hover:border-b-gray-700 flex items-center justify-end gap-2 hover:cursor-pointer duration-300 w-full"
            title={t('common.add_to_cart')}
            onClick={() => handleAddToCart(product.id)}>
            {t('common.add_to_cart')}
            <Icon component={ShoppingCart} />
          </li>
          <li
            className="text-gray-600 hover:text-black text-sm font-medium border-b-[1px] border-b-gray-400 hover:border-b-gray-700 flex items-center justify-end gap-2 hover:cursor-pointer duration-300 w-full"
            title={t('common.view_detail')}
            onClick={() => router.push(`/product/${product.slug}`)}>
            {t('common.view_detail')}
            <Icon component={Source} />
          </li>
          {wishlist?.product.find((item: Wishlist) => item.id === product.id) === undefined ? (
            <li
              className="text-gray-600 hover:text-black text-sm font-medium border-b-[1px] border-b-gray-400 hover:border-b-gray-700 flex items-center justify-end gap-2 hover:cursor-pointer duration-300 w-full"
              title={t('common.add_to_wishlist')}
              onClick={() => handleAddToWishlist(product.id)}>
              {t('common.add_to_wishlist')}
              <Icon component={HeartEmpty} />
            </li>
          ) : (
            <li
              className="text-gray-600 hover:text-black text-sm font-medium border-b-[1px] border-b-gray-400 hover:border-b-gray-700 flex items-center justify-end gap-2 hover:cursor-pointer duration-300 w-full"
              title={t('common.remove_from_wishlist')}
              onClick={() => handleRemoveFromWishlist(product.id)}>
              {t('common.remove_from_wishlist')}
              <Icon component={HeartFull} />
            </li>
          )}
        </ul>
      </div>

      <div className="relative z-50 bg-white">
        <h1 className="truncate opacity-70 hover:opacity-100 my-2" title={product.name}>
          {product.name}
        </h1>

        <div className="mb-5 flex justify-end font-medium text-yellow-500 text-lg">
          <span>${product.price}</span>
        </div>

        <div className="flex justify-between">
          <span className="flex items-center">
            <Rating precision={0.1} value={ratingStar.value} size="small" readOnly />
            <span className="ml-1">{ratingStar.value.toFixed(1)}</span>
          </span>

          <span className="mr-1">
            {roundNumber(ratingStar.vote)} {t('product.rating')}
          </span>
        </div>

        <span className="italic ml-2">
          {t('product.Sold')}: {sold}
        </span>

        <Button className="w-full bg-yellow-400" onClick={() => handleAddToCart(product.id)}>
          {t('common.add_to_cart')}
        </Button>
      </div>
    </Card>
  );
};

export default ProductItemCard;
