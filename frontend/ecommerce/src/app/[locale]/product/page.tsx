'use client'

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import BreadcrumbComponent from '@/components/Breadcrumb/Breadcrumb';
import { Breadcrumb, PaginationParams } from '@/models/types';

const crumb: Breadcrumb[] = [
  {
    pageName: 'Home',
    url: '/',
  },
  {
    pageName: 'Product',
    url: '#',
  },
];

export default function ProductPage() {
  const router = useRouter();

  const [pagination, setPagination] = useState<PaginationParams>({page: 0, limit: 10});
  const [filters, setFilters] = useState<any>(null);

  const handleFilter = (key: string, value: string | number) => {
    setPagination({...pagination, page: 0})
    router.push({
      pathname: '/products',
      query: {
        ...filters,
        [key]: value,
      },
    });
  };

  return (
    <React.Fragment>
      <BreadcrumbComponent items={crumb} />
      <div>aaaaaaaaaaaaaaaaaa</div>
    </React.Fragment>
  );
}
