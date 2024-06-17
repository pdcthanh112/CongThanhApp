import { Box, Skeleton } from '@mui/material';
import React from 'react';

const NotificationItemSkeleton = () => {
  return (
    <Box display={'flex'}>
      <Skeleton variant="circular" width={70} height={70} />
      <div className="w-full ml-3 relative">
        <Skeleton />
        <Skeleton height={50} />
        {/* <Skeleton width={100} sx={{ position: 'absolute', right: 0 }} /> */}
      </div>
    </Box>
  );
};

export default NotificationItemSkeleton;
