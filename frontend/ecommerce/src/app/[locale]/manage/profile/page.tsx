import React from 'react';
import Image from 'next/image';
import DefaultImage from '@/assets/images/default-image.jpg';
import EditProfile from './EditProfile';
import { getServerSession } from 'next-auth';

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

async function getUserInfo(email: string) {
  return await getUserInfo(email);
}

const userInfo = {
  email: 'pdcthanh112.dev@gmail.com',
  name: 'nameeeeeeeeeeeee',
  image: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Leucanthemum_vulgare_%27Filigran%27_Flower_2200px.jpg',
  phone: '0123456789',
  dob: '01/12/2000',
  address: 'hjha ljjlasjf jasljf jlasjf ạlfjfjf',
  gender: "MALE"
};

export default async function ProfilePage() {
  const session = await getServerSession();
  console.log('SSSSSSSSSSSSSSSSSSSSSS', session);
  const user  = session?.user;

  if (!user) return <div>Login</div>;
  // const userInfo = await getUserInfo(user.email as string);

  return (
    <React.Fragment>
      <div className="px-3 py-2">
        <div className="border-b-2 border-gray-200 pb-2 flex justify-between">
          <div>
            <h3 className="font-medium text-lg">Manage your profile</h3>
            <span className="opacity-90">Quản lý thông tin hồ sơ để bảo mật tài khoản</span>
          </div>
        </div>
        <div className="w-4/5 mx-auto my-4 flex justify-between bg-red">
          <div>
            <div className="flex justify-between">
              <InformationField title={'Name'} className="w-96">
                <div>{userInfo.name}</div>
              </InformationField>
              <InformationField title={''}>
                <Image src={userInfo.image || DefaultImage} alt={'User image'} width={150} height={150} />
              </InformationField>
            </div>

            <InformationField title={'Email'} className="w-80">
              <div>{userInfo.email}</div>
            </InformationField>

            <div className="flex">
              <InformationField title={'Phone'} className="w-56">
                <div>{userInfo.phone}</div>
              </InformationField>
              <InformationField title={'Date of birth'} className="w-48 ml-10">
                <input type="date" defaultValue={userInfo.dob.toString()} disabled />
              </InformationField>
            </div>

            <InformationField title={'Address'} className="">
              <div>{userInfo.address}</div>
            </InformationField>
          </div>
        </div>
      </div>
      <EditProfile userInfo={user}/>
    </React.Fragment>
  );
}
