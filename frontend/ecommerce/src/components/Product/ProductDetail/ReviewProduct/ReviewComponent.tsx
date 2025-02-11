import React from 'react';
import { Review } from '@/models/types/Review';
import { Avatar, AvatarFallback, AvatarImage, Separator } from '@/components/ui';
import { Rating } from '@mui/material';
import moment from 'moment';
import Image from 'next/image';

type PropsType = {
  review: Review;
};

export default function ReviewComponent({ review }: PropsType) {
  console.log('RRRRRRRRRRRRRRRRRRRRRRRRRRRR', review);
  return (
    <React.Fragment>
      <div className="flex items-center">
        <Avatar>
          <AvatarImage src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvTZzRBQJr3hGRmgOafgRHzNAvWYXA3RY3_g&s" />
          <AvatarFallback>CN</AvatarFallback>
        </Avatar>{' '}
        <span>Customer name</span>
      </div>
      <div className="flex text-sm">
        <Rating value={4} readOnly size="small" /> <Separator orientation="vertical" className="mx-2" />
        <span className="">{moment(review.createdAt).format('YYYY-MMM-DD hh:mm')}</span>
        <Separator orientation="vertical" className="mx-2" />
        <span>Phân loại: Iphone 16 pro max màu xám</span>
      </div>
      <div>{review.content}</div>
      {review.reviewMedia && (
        <div className='flex'>
          {review.reviewMedia.map((item) => (
            <span key={item.id} className="w-24 h-24 relative mr-2">
              <Image src={item.url} alt={''} objectFit='contain' fill/>
            </span>
          ))}
        </div>
      )}
    </React.Fragment>
  );
}
