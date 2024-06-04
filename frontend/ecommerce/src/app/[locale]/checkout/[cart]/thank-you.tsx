import { serverSideTranslations } from 'next-i18next/serverSideTranslations';
import Link from 'next/link';

const ThankYou = () => {
  return (
    <div className="w-screen h-[38rem] bg-white flex justify-center items-center">
      <div className="w-1/2 h-1/2 text-center">
        <p className="text-2xl">Your checkout have been successfully</p>
        <p>
          You can tracking your order <Link href={'/order'} className='hover:cursor-pointer hover:underline'>here</Link>
        </p>
        <Link href={'/'} className='hover:cursor-pointer hover:underline'>Home</Link>
      </div>
    </div>
  );
};

export default ThankYou;

export async function getServerSideProps(context: any) {
  return {
    props: {
      ...(await serverSideTranslations(context.locale, ['common'])),
    },
  };
}
