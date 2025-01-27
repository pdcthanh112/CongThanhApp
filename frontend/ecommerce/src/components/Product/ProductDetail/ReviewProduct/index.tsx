'use client';
import { getReviewByProduct } from '@/api/reviewApi';
import { Review } from '@/models/types/Review';
import { useQuery } from '@tanstack/react-query';

type PropsType = {
  productId: string;
};

export default function ReviewProduct({ productId }: PropsType) {
  // const { data: reviews } = useQuery({
  //   queryKey: ['review'],
  //   queryFn: async () => await getReviewByProduct(productId),
  // });
  const reviews: Review[] = [
    {
      id: 34234080593,
      content:
        'Sản phẩm đẹp , đúng mẫu . Không biết thời gian sử dụng có bị ố vàng hay không . Nhưng trước mắt là thấy tuyệt ',
      rating: 5,
      customerId: '7ogh-34052here7280-g423',
      product: 'j;df-fgdf-sfdsgfsd',
      reviewMedia: [
        {
          id: 0,
          url: 'https://www.usnews.com/object/image/00000162-f3bb-d0d5-a57f-fbfb3eef0000/32-lake-louise.jpg?update-time=1677094961403&size=responsive640',
        },
        {
          id: 0,
          url: 'https://www.usnews.com/object/image/00000186-7a58-d975-aff7-fffbc8910000/iguazu-falls-stock.jpg?update-time=1677089883729&size=responsive640',
        },
        {
          id: 0,
          url: 'https://www.usnews.com/object/image/00000162-f3a3-d0d5-a57f-fbf3af9b0000/20-mount-rainier-national-park.jpg?update-time=1677094377415&size=responsive640',
        },
      ],
    },
    {
      id: 34234080593,
      content:
        'Sản phẩm đẹp , đúng mẫu . Không biết thời gian sử dụng có bị ố vàng hay không . Nhưng trước mắt là thấy tuyệt ',
      rating: 4,
      customerId: '7ogh-34052here7280-g423',
      product: 'j;df-fgdf-sfdsgfsd',
      reviewMedia: [
        {
          id: 0,
          url: 'https://www.usnews.com/object/image/00000162-f3bb-d0d5-a57f-fbfb3eef0000/32-lake-louise.jpg?update-time=1677094961403&size=responsive640',
        },
        {
          id: 0,
          url: 'https://www.usnews.com/object/image/00000186-7a58-d975-aff7-fffbc8910000/iguazu-falls-stock.jpg?update-time=1677089883729&size=responsive640',
        },
        {
          id: 0,
          url: 'https://www.usnews.com/object/image/00000162-f3a3-d0d5-a57f-fbf3af9b0000/20-mount-rainier-national-park.jpg?update-time=1677094377415&size=responsive640',
        },
      ],
    },
  ];

  return <div>àaaa</div>;
}
