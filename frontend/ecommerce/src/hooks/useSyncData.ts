'use client'

import { useEffect, useState, useCallback } from 'react'
import { useSession } from 'next-auth/react'
import { useDispatch } from 'react-redux'
import { setCartItems, setLoading } from '@/store/features/cart/cartSlice'
import { useWishlistStore } from '@/store/wishlistStore'
import { fetchUserCart, fetchUserWishlist } from '@/app/actions/user-data'

export function useSyncUserData() {
  const { data: session, status } = useSession()
  const dispatch = useDispatch()
  const [isSyncing, setIsSyncing] = useState(false)
  const [error, setError] = useState(null)
  
  const setWishlistItems = useWishlistStore(state => state.setItems)
  const setWishlistLoading = useWishlistStore(state => state.setLoading)

  const syncData = useCallback(async () => {
    if (status !== 'authenticated') return
    
    setIsSyncing(true)
    setError(null)
    
    // Set loading states
    dispatch(setLoading(true))
    setWishlistLoading(true)
    
    try {
      const [cartData, wishlistData] = await Promise.all([
        fetchUserCart(),
        fetchUserWishlist()
      ])
      
      // Update Redux store
      dispatch(setCartItems(cartData))
      
      // Update Zustand store
      setWishlistItems(wishlistData)
      
    } catch (error) {
      setError(error.message || 'Failed to sync user data')
      console.error('Sync error:', error)
    } finally {
      setIsSyncing(false)
      dispatch(setLoading(false))
      setWishlistLoading(false)
    }
  }, [status, dispatch, setWishlistItems, setWishlistLoading])

  // Sync automatically when session becomes authenticated
  useEffect(() => {
    if (status === 'authenticated') {
      syncData()
    }
  }, [status, syncData])

  return { syncData, isSyncing, error }
}