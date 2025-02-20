// import { useTranslations } from 'next-intl';
import ShowListCart from '@/components/Cart/ShowListCart';
import { getCartByCustomerId } from '@/api/cartApi';
import Link from 'next/link';
import { getServerSession } from 'next-auth/next';

async function getCartData(customerId: string) {
  return await getCartByCustomerId(customerId).then(response => response.data);
}

export default async function CartPage() {
  const session  = await getServerSession();
  console.log('SSSSSSSSSSSSSs', session)

  if (!session || !session.user) {
    return (
      <div className="w-full p-4">
        <div className="p-4 text-center bg-yellow-100 rounded-md">
          <h3 className="text-lg font-medium">Vui lòng đăng nhập để xem giỏ hàng</h3>
          <Link href="/login?callbackUrl=/cart" className="mt-2 text-blue-600 hover:underline">
            Đăng nhập ngay
          </Link>
        </div>
      </div>
    );
  }

  const data = await getCartData("6304f010-3985-4ecc-a139-e8aba1eee7b1");

  return (
    <div className="w-4/5 mx-auto">
      <h3 className="px-5 py-2 mb-3 text-3xl bg-white">{'cart.your_cart'}</h3>
      <ShowListCart data={data} loading={false} />
    </div>
  );
}
