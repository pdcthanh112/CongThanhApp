"use client"
import QuantitySelector from '@/components/quantity-selector';
import ShowWishlistItem from '@/components/Wishlist/ShowWishlistItem';
import { useState } from 'react';

export default  function WishlistPage() {
  const [selectedQuantity, setSelectedQuantity] = useState(1);
  // return <ShowWishlistItem />;
  return <QuantitySelector max={5} min={1} value={selectedQuantity} onIncrease={(selectedQuantity) => setSelectedQuantity(selectedQuantity + 1)}/>
}
