import { create } from 'zustand';
import { Product } from '@/models/types';

interface WishlistStore {
  items: Product[];
  setWishlist: (items: Product[]) => void;
  addToWishlist: (item: Product) => void;
  removeFromWishlist: (productId: string) => void;
}

export const useWishlistStore = create<WishlistStore>((set) => ({
  items: [],
  setWishlist: (items) => set({ items }),
  addToWishlist: (item) =>
    set((state) => ({
      items: [...state.items, item],
    })),
  removeFromWishlist: (productId) =>
    set((state) => ({
      items: state.items.filter((item) => item.id !== productId),
    })),
}));
