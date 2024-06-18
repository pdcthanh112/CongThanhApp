'use client';
import React, { useRef, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { useQuery } from '@tanstack/react-query';
import { getImageByProductId, getSoldByProduct, getVariantAttributeValueByProduct } from 'api/productApi';
import { Rating, Icon, Avatar, TableContainer, Table, TableBody, TableRow, TableCell, Popover } from '@mui/material';
import { Storefront, ForumOutlined, KeyboardArrowDown } from '@mui/icons-material';
import Image from 'next/image';
import DefaultImage from '@/assets/images/default-image.jpg';
import { formatCurrency, roundNumber } from '@/utils/helper';
import { useTranslations } from 'next-intl';
import { useAddProductToWishlist, useRemoveProductFromWishlist } from '@/hooks/wishlist/wishlistHook';
import { useAppDispatch, useAppSelector } from '@/redux/store';
import { openModalAuth } from '@/redux/features/modalAuth';
import { HeartEmpty, HeartFull } from '@/assets/icons';
import { toast } from 'react-toastify';
import { useAddProductToCart } from '@/hooks/cart/cartHook';
import { getWishlistByCustomer } from 'api/wishlistApi';
import { getSupplierById } from '@/api/supplierApi';
import Link from 'next/link';
import { Customer, Product, ProductAttribute, ProductImage, Supplier, Wishlist } from '@/models/types';
import QuantitySelector from '@/components/QuantitySelector/QuantitySelector';
import { PRODUCT_KEY, WISHLIST_KEY } from '@/utils/constants/queryKey';
import ProductDetailSkeleton from '../ProductDetailSkeleton';
import { ReviewStatistic } from '@/models/types/Review';
import { BarChart, Bar, XAxis, ResponsiveContainer } from 'recharts';
import { Button } from '@/components/ui/button';
import { PATH } from '@/utils/constants/path';

type ProductDetailProps = {
  product: Product;
  reviewStatistic: ReviewStatistic;
  supplier: Supplier;
};

export default function ProductDetail({ product, reviewStatistic, supplier }: ProductDetailProps) {
  console.log('PPPPPPPPPPPPPPP', product);
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);
  const router = useRouter();
  const params = useParams();

  const dispatch = useAppDispatch();
  const t = useTranslations();

  const [quantity, setQuantity] = useState(1);
  const [sold, setSold] = useState<number>(0);

  // const [showReviewStatistic, setShowReviewStatistic] = useState<boolean>(false);
  const [currentImage, setCurrentImage] = useState<ProductImage>();
  const [currentIndexImages, setCurrentIndexImages] = useState([0, 5]);
  const [anchorEl, setAnchorEl] = React.useState<HTMLDivElement | null>(null);

  const imageRef = useRef<HTMLImageElement>(null);

  const { mutate: addProductToCart } = useAddProductToCart();

  const { mutate: addProductToWishlist } = useAddProductToWishlist();
  const { mutate: removeProductFromWishlist } = useRemoveProductFromWishlist();

  const { data: productVariantAttribute } = useQuery({
    queryKey: ['list-product-attribute-value'],
    queryFn: async () => await getVariantAttributeValueByProduct(product.id).then((response) => response.data),
  });

  //   const { data: product, isLoading } = useQuery({
  //     queryKey: [PRODUCT_KEY, productSlug],
  //     queryFn: async () => await getProductBySlug(productSlug as string).then(async(result) => {
  //           await getRatingStarofProduct(result.id).then((response) => {
  //             if (response && response.data) {
  //               setRatingStar(response.data);
  //             }
  //           });
  //           await getStoreById(result.store).then((response) => {
  //             if (response && response.data) {
  //               setStore(response.data);
  //             }
  //           });
  //           await getAttributeByProductId(result.id).then((response) => {
  //             if (response && response.data) {
  //               setProductAttribute(response.data);
  //             }
  //           });
  //           await getImageByProductId(result.id).then((response) => {
  //             if (response && response.data) {
  //               setProductImage(response.data);
  //               setCurrentImage(response.data.find((image: ProductImage) => image.isDefault === true));
  //             }
  //           });
  //           await getSoldByProduct(result.id).then((response) => {
  //             if (response && response.data) {
  //               setSold(response.data);
  //             }
  //           });
  //           return result.data
  //         },
  //    )
  // });

  const { data: wishlist } = useQuery<Wishlist>({
    queryKey: [WISHLIST_KEY],
    queryFn: async () => await getWishlistByCustomer(currentUser.userInfo.accountId).then((result) => result.data),
  });

  const handleAddProductToCart = () => {
    if (currentUser) {
      try {
        addProductToCart(
          { productId: product.id, quantity: quantity, cartId: '36c98af9-bee7-4e11-bd19-36426261d727' },
          {
            onSuccess() {
              toast.success(t('cart.add_item_to_cart_successfully'));
            },
            onError(error) {
              toast.error(t('cart.add_item_to_cart_failed'));
              console.log(error);
            },
          }
        );
      } catch (error) {
        toast.error(t('cart.add_item_to_cart_failed'));
      }
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
            onError() {
              toast.error(t('wishlist.add_item_to_wishlist_failed'));
            },
          }
        );
      } catch (error) {
        toast.error(t('wishlist.add_item_to_wishlist_failed'));
      }
    } else {
      dispatch(openModalAuth());
    }
  };

  const handleRemoveFromWishlist = (productId: string) => {
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
          }
        );
      } catch (error) {
        toast.error(t('wishlist.remove_item_from_wishlist_failed'));
      }
    } else {
      dispatch(openModalAuth());
    }
  };

  const handleZoom = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    const rect = event.currentTarget.getBoundingClientRect();

    const image = imageRef.current as HTMLImageElement;
    const { naturalHeight, naturalWidth } = image;
    const { offsetX, offsetY } = event.nativeEvent;
    const top = offsetY * (1 - naturalHeight / rect.height);
    const left = offsetX * (1 - naturalWidth / rect.width);
    image.style.width = naturalWidth + 'px';
    image.style.height = naturalHeight + 'px';
    image.style.maxWidth = 'unset';
    image.style.top = top + 'px';
    image.style.left = left + 'px';
  };

  const handleRemoveZoom = () => {
    imageRef.current?.removeAttribute('style');
  };

  const next = () => {
    if (currentIndexImages[1] < 10) {
      setCurrentIndexImages([currentIndexImages[0] + 1, currentIndexImages[1] + 1]);
    }
  };

  const prev = () => {
    if (currentIndexImages[0] > 0) {
      setCurrentIndexImages([currentIndexImages[0] - 1, currentIndexImages[1] - 1]);
    }
  };

  const data = [
    {
      name: '5 stars',
      uv: Math.round((reviewStatistic.reviewRating5Star / reviewStatistic.totalReview) * 100),
    },
    {
      name: '4 stars',
      uv: Math.round((reviewStatistic.reviewRating4Star / reviewStatistic.totalReview) * 100),
    },
    {
      name: '3 stars',
      uv: Math.round((reviewStatistic.reviewRating3Star / reviewStatistic.totalReview) * 100),
    },
    {
      name: '2 stars',
      uv: Math.round((reviewStatistic.reviewRating2Star / reviewStatistic.totalReview) * 100),
    },
    {
      name: '1 stars',
      uv: Math.round((reviewStatistic.reviewRating1Star / reviewStatistic.totalReview) * 100),
    },
  ];

  return (
    <div className="w-[80%] mx-auto my-3">
      <div className="bg-white flex px-3 py-2">
        <div className="w-[40%] py-3">
          <div
            className="relative w-full pt-[100%] shadow cursor-zoom-in overflow-hidden"
            onMouseLeave={handleRemoveZoom}
            onMouseMove={handleZoom}
          >
            <Image
              src={currentImage?.imagePath || DefaultImage}
              alt={currentImage?.alt || product.name}
              width={300}
              height={300}
              ref={imageRef}
              className="absolute pointer-events-none top-0 left-0 h-full w-full bg-white object-cover"
            />
          </div>
          <div className="relative mt-4 grid grid-cols-5 gap-2 h-28 overflow-hidden">
            <button
              onClick={prev}
              className="absolute left-0 top-1/2 z-10 h-9 w-5 -translate-y-1/2 bg-black/20 text-white"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="w-5 h-5"
              >
                <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
              </svg>
            </button>
            <button
              onClick={next}
              className="absolute right-0 top-1/2 z-10 h-9 w-5 -translate-y-1/2 bg-black/20 text-white"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="w-5 h-5"
              >
                <path strokeLinecap="round" strokeLinejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
              </svg>
            </button>
            {product.image?.slice(...currentIndexImages).map((img: any) => {
              const isActive = img === currentImage;
              return (
                <div key={img.id} className="relative w-full pt-[100%]" onClick={() => setCurrentImage(img)}>
                  <Image
                    src={img.imagePath}
                    alt={img.alt || product.name}
                    fill
                    objectFit="fill"
                    className={`cursor-pointer absolute top-0 left-0 h-full w-full bg-white object-cover ${
                      isActive && 'border-2 border-red-400'
                    }`}
                  />
                </div>
              );
            })}
          </div>
        </div>
        <div className="w-[60%] ml-5 p-3">
          <h1 className="font-medium text-2xl bg-gray-200 px-3 py-2 mb-3">{product.name}</h1>
          <div className="flex justify-between my-3">
            <React.Fragment>
              <div className="flex items-center">
                <div
                  className="flex items-center"
                  onClick={(event) => {
                    setAnchorEl(event.currentTarget);
                  }}
                >
                  <span className="mr-1">{reviewStatistic?.averageRating}</span>
                  <Rating value={reviewStatistic.averageRating} precision={0.1} size="small" readOnly />
                  <Icon component={KeyboardArrowDown} />
                </div>
                <span className="opacity-80 mx-2">|</span>
                <span>
                  {roundNumber(reviewStatistic.totalReview)} {t('product.rating')}
                </span>
                <span className="opacity-80 mx-2">|</span>
                <span>
                  {t('product.sold')}: {sold}
                </span>
              </div>
              <Popover
                open={Boolean(anchorEl)}
                anchorEl={anchorEl}
                onClose={() => {
                  setAnchorEl(null);
                }}
                anchorOrigin={{
                  vertical: 'bottom',
                  horizontal: 'center',
                }}
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'center',
                }}
              >
                <div className="px-3 py-2 w-96 h-80">
                  <div className="flex items-center">
                    <Rating value={reviewStatistic.averageRating} precision={0.1} size="small" readOnly />
                    <span className="mr-1">{reviewStatistic?.averageRating} out of 5</span>
                  </div>
                  <div>{reviewStatistic.totalReview} ratings</div>
                  <div>
                    {/* <ResponsiveContainer width="100%" height="100%"> */}
                    <BarChart width={300} height={400} data={data} layout="vertical">
                      <Bar dataKey="uv" barSize={20} fill="#8884d8" label />
                      <XAxis hide />
                      {/* <YAxis type="category" dataKey="name" /> */}
                    </BarChart>
                    {/* </ResponsiveContainer> */}
                  </div>
                </div>
              </Popover>
            </React.Fragment>
            <div>
              {wishlist?.product.find((item) => item.id === product.id) === undefined ? (
                <span
                  className="hover:cursor-pointer"
                  title={t('common.add_to_wishlist')}
                  onClick={() => handleAddToWishlist(product.id)}
                >
                  <Icon component={HeartEmpty} sx={{ color: 'red' }} />
                </span>
              ) : (
                <span
                  className="hover:cursor-pointer"
                  title={t('common.remove_from_wishlist')}
                  onClick={() => handleRemoveFromWishlist(product.id)}
                >
                  <Icon component={HeartFull} sx={{ color: 'red' }} />
                </span>
              )}
            </div>
          </div>
          <div className="font-semibold text-3xl text-yellow-400">{formatCurrency(10000000000000, 'vi', 'VND')}</div>

          <div>
            {productVariantAttribute?.map((item) => (
              <div key={item.id} className="flex space-y-5">
                <div className="w-1/5 flex items-center">{item.attributeName}</div>
                <div className="w-4/5">
                  {item.value.map((item) => (
                    <span key={item.id} className="border border-gray-200 rounded px-3 py-2 mr-3 hover:cursor-pointer">
                      {item.value}
                    </span>
                  ))}
                </div>
              </div>
            ))}
          </div>

          <div className="flex mt-10">
            <span className="flex items-center mr-10">{t('product.Quantity')}</span>
            <QuantitySelector
              value={quantity}
              max={123456}
              onDecrease={setQuantity}
              onIncrease={setQuantity}
              onType={setQuantity}
            />
            <span className="flex items-center ml-10">
              {product ? <p>12345 {t('common.available')}</p> : <p>{t('common.sold_out')}</p>}
            </span>
          </div>
          <div className="flex mt-10">
            <Button
              className="bg-yellow-50 border-yellow-300 border-2 text-yellow-400"
              onClick={() => handleAddProductToCart()}
            >
              <AddToCartIcon width={28} height={28} />
              <span className="ml-1">{t('common.add_to_cart')}</span>
            </Button>
            <Button className="bg-yellow-400 text-[#fff] ml-3" disabled={12345 <= 0}>
              <span className="mx-3">{t('product.buy_now')}</span>
            </Button>
          </div>
        </div>
      </div>
      <div className="bg-white mt-10 p-5 flex">
        <div className="w-[35%] flex">
          <Avatar src={supplier.avatar} alt="Store Avatar" style={{ width: '6rem', height: '6rem' }} />
          <div className="w-full ml-3">
            <h3 className="mb-2">{supplier.name}</h3>
            <div className="flex">
              <Button className="bg-yellow-50 text-yellow-300 border-solid border-2 border-yellow-300 mr-3">
                <Icon component={ForumOutlined} />
                <span>{t('common.contact')}</span>
              </Button>
              <Link
                href={PATH.SUPPLIER_PATH_URL.SUPPLIER_DATAIL + supplier.slug}
                className=" border-solid border-2 border-gray-300 flex items-center px-2"
              >
                <Icon component={Storefront} />
                <span>{t('store.view_store')}</span>
              </Link>
            </div>
          </div>
        </div>
        <div className="bg-red-400 w-[65%]">asdfasjl</div>
      </div>
      <div className="bg-white mt-10 p-5">
        <h2 className="bg-yellow-100 px-2 py-1 rounded-sm">{t('product.product_detail').toUpperCase()}</h2>
        <div className="grid grid-cols-4">
          <div className="col-span-1">{t('common.Category')}</div>
          <div className="col-span-3">{product.category}</div>
          <div className="col-span-1">{t('common.Subcategory')}</div>
          <div className="col-span-3">{product.subcategory}</div>
          <div className="col-span-1">{t('common.in_stock')}</div>
          {/* <div className="col-span-3">{product.quantity > 0 ? <p>{product.quantity}</p> : <p>0</p>}</div> */}
        </div>
      </div>
      <div className="bg-white mt-10 p-5">
        <h2 className="bg-yellow-100 px-2 py-1 rounded-sm">{t('product.product_attribute').toUpperCase()}</h2>
        <TableContainer>
          <Table>
            <TableBody>
              {product.attribute?.map((item: ProductAttribute) => (
                <TableRow key={item.id}>
                  <TableCell>{item.attribute}</TableCell>
                  <TableCell>{item.value}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
      <div className="bg-white mt-10 p-5">
        <h2 className="bg-yellow-100 px-2 py-1 rounded-sm">{t('product.product_description').toUpperCase()}</h2>
        <div className="">{product.description}</div>
      </div>
    </div>
  );
}

const AddToCartIcon = (props: React.SVGProps<SVGSVGElement>) => (
  <svg baseProfile="tiny" viewBox="0 0 24 24" fill="currentColor" height="1em" width="1em" {...props}>
    <path d="M20.756 5.345A1.003 1.003 0 0020 5H6.181l-.195-1.164A1 1 0 005 3H2.75a1 1 0 100 2h1.403l1.86 11.164.045.124.054.151.12.179.095.112.193.13.112.065a.97.97 0 00.367.075H18a1 1 0 100-2H7.847l-.166-1H19a1 1 0 00.99-.858l1-7a1.002 1.002 0 00-.234-.797zM18.847 7l-.285 2H15V7h3.847zM14 7v2h-3V7h3zm0 3v2h-3v-2h3zm-4-3v2H7l-.148.03L6.514 7H10zm-2.986 3H10v2H7.347l-.333-2zM15 12v-2h3.418l-.285 2H15z" />
    <path d="M10 19.5 A1.5 1.5 0 0 1 8.5 21 A1.5 1.5 0 0 1 7 19.5 A1.5 1.5 0 0 1 10 19.5 z" />
    <path d="M19 19.5 A1.5 1.5 0 0 1 17.5 21 A1.5 1.5 0 0 1 16 19.5 A1.5 1.5 0 0 1 19 19.5 z" />
  </svg>
);
