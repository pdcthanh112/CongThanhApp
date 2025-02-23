import { getCartById } from '@/api/cartApi';
import CheckoutComponent from './CheckoutComponent';

async function getCart(cartId: number) {
  return await getCartById(cartId).then((response) => response.data);
}

export default async function CheckoutPage({ params }: { params: Promise<{ cart: number }> }) {
  const cartData = await getCart((await params).cart);
  return <CheckoutComponent cart={cartData} />;
}
