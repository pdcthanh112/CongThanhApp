import { NextPage } from 'next';
import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import { getProductFromSupplier, getSupplierById } from '@/api/supplierApi';
import { useRouter } from 'next/router';
import { Product } from '@models/type/ProductModel';
import { PaginationParams } from '@models/type';
import Image from 'next/image';
import { Avatar, Icon } from '@mui/material';
import Button from '@components/UI/Button';
import { Add, ForumOutlined } from '@mui/icons-material';
import { Tabs, type TabsProps } from 'antd';
import { useTranslations } from 'next-intl';

const Store: NextPage = (): React.ReactElement => {
  const router = useRouter();
  const { id: storeId } = router.query;

  const t = useTranslations('common');

  const [pagination, setPagination] = useState<PaginationParams>({ page: 1, limit: 10, totalPage: 0 });

  const { data: store, isLoading } = useQuery(['store'], async () => await getSupplierById(storeId).then((response) => response.data));
  const { data: listProduct, isLoading: isLoadingProduct } = useQuery(
    ['product', pagination],
    async () =>
      await getProductFromSupplier(storeId, 0, 15).then((response) => {
        setPagination({ ...pagination, totalPage: response.data.totalPage });
        return response.data.responseList;
      }),
  );

  const items: TabsProps['items'] = [
    {
      key: '1',
      label: 'Best seller',
      children: 'Content of Tab Pane 1',
    },
    {
      key: '2',
      label: 'All',
      children: 'Content of Tab Pane 2',
    },
    {
      key: '3',
      label: 'Tab 3',
      children: 'Content of Tab Pane 3',
    },
  ];


  if (isLoading || isLoadingProduct) return <div>loading</div>;

  return (
    <React.Fragment>
      <div className="bg-white flex justify-center py-5">
        <div className="w-[80%] flex">
          <div className="w-1/3 relative">
            <Image src={store.background} alt={'Store background'} width={350} height={100} className="relative opacity-90" />
            <div className="absolute top-0 left-0 w-full h-full bg-opacity-50 z-10 p-4 text-white">
              <div className="flex">
                <Avatar src={store.avatar} style={{ width: '5rem', height: '5rem' }} />
                <div className=" ml-2">
                  <h3 className="font-medium text-lg">{store.name}</h3>
                  <span>asljlajslasfjl</span>
                </div>
              </div>

              <div className="flex">
                <Button className=" border-solid border-2 border-gray-300 flex items-center mr-3">
                  <Icon component={Add} />
                  <span>{t('common.follow')}</span>
                </Button>
                <Button className="border-solid border-2">
                  <Icon component={ForumOutlined} />
                  <span>{t('common.contact')}</span>
                </Button>
              </div>
            </div>
          </div>
          <div className="bg-yellow-300 w-2/3">asdf</div>
        </div>
      </div>
      <Tabs defaultActiveKey="2" items={items} />


      <div>
        {listProduct.map((item: Product) => (
          <>{item.name}</>
        ))}
      </div>
      
    </React.Fragment>
  );
};

export default Store;

export async function getServerSideProps({ locale }: any) {
  return {
    props: {
      ...(await serverSideTranslations(locale, ['common'])),
    },
  };
}
