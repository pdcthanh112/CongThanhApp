import React, { useState } from 'react';
import ManagementLayout from '@layout/ManagementLayout';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import { useTranslation } from 'next-i18next';
import { Customer } from '@models/type';
import { useAppSelector } from '@redux/store';
import Image from 'next/image';
import DefaultImage from '@assets/images/default-image.jpg';
import { Icon } from '@mui/material';
import { Edit } from '@mui/icons-material';
import EditProfile from './EditProfile';

type InformationFieldProps = {
  title: string;
  children: React.ReactNode;
  className?: string;
};

const InformationField: React.FC<InformationFieldProps> = (element) => {
  return (
    <div className={`mb-3 h-20 ${element.className}`}>
      <h4 className="font-medium">{element.title}</h4>
      <div className="border border-gray-300 px-3 py-2">{element.children}</div>
    </div>
  );
};

const Profile = (): React.ReactElement => {
  const currentUser: Customer = useAppSelector((state) => state.auth.currentUser);

  const [isOpenModalEdit, setIsOpenModalEdit] = useState<boolean>(true);

  const { t } = useTranslation('common');
  return (
    <React.Fragment>
      <div className="px-3 py-2">
        <div className="border-b-2 border-gray-200 pb-2 flex justify-between">
          <div>
            <h3 className="font-medium text-lg">Manage your profile</h3>
            <span className="opacity-90">Quản lý thông tin hồ sơ để bảo mật tài khoản</span>
          </div>
          <Icon titleAccess='Edit' component={Edit} onClick={() => setIsOpenModalEdit(true)} className="opacity-80 hover:opacity-100 hover:cursor-pointer" />
        </div>
        <div className="w-4/5 mx-auto my-4 flex justify-between bg-red">
          <div>
            <div className="flex justify-between">
              <InformationField title={'Name'} className="w-96">
                <div>{currentUser.userInfo.name}</div>
              </InformationField>
              <InformationField title={''}>
                <Image src={currentUser.userInfo.image || DefaultImage} alt={'User image'} width={150} height={150} />
              </InformationField>
            </div>

            <InformationField title={'Email'} className="w-80">
              <div>{currentUser.userInfo.email}</div>
            </InformationField>

            <div className="flex">
              <InformationField title={'Phone'} className="w-56">
                <div>{currentUser.userInfo.phone}</div>
              </InformationField>
              <InformationField title={'Date of birth'} className="w-48 ml-10">
                <input type="date" defaultValue={currentUser.userInfo.dob.toString()} disabled />
              </InformationField>
            </div>

            <InformationField title={'Address'} className="">
              <div>{currentUser.userInfo.address}</div>
            </InformationField>
          </div>
        </div>
      </div>
      <EditProfile isOpen={isOpenModalEdit} handleOpen={setIsOpenModalEdit} />
    </React.Fragment>
  );
};

Profile.getLayout = function getLayout(page: React.ReactNode) {
  return <ManagementLayout>{page}</ManagementLayout>;
};

export default Profile;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}
