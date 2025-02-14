import { Customer } from '@/models/types';
import { getAccessTokenFromLocalStorage, getProfileFromLocalStorage } from '@/utils/auth';
import { createContext, useState } from 'react';

interface AppContextInterface {
  isAuthenticated: boolean;
  setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>;
  profile: Customer | null;
  setProfile: React.Dispatch<React.SetStateAction<Customer | null>>;
  //   extendedPurchase: ExtendedPurchase[]
  //   setExtendedPurchase: React.Dispatch<React.SetStateAction<ExtendedPurchase[]>>
  reset: () => void;
}
const initialAppContext: AppContextInterface = {
  isAuthenticated: Boolean(getAccessTokenFromLocalStorage()),
  setIsAuthenticated: () => null,
  profile: getProfileFromLocalStorage(),
  setProfile: () => null,
  //   extendedPurchase: [],
  //   setExtendedPurchase: () => null,
  reset: () => null,
};
export const AppContext = createContext<AppContextInterface>(initialAppContext);

export const AppContextProvider = ({ children }: { children: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(initialAppContext.isAuthenticated);
  const [profile, setProfile] = useState<Customer | null>(initialAppContext.profile);
  //   const [extendedPurchase, setExtendedPurchase] = useState<ExtendedPurchase[]>(initialAppContext.extendedPurchase)

  const reset = () => {
    setIsAuthenticated(false);
    setProfile(null);
    // setExtendedPurchase([])
  };

  return (
    <AppContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        profile,
        setProfile,
        //  extendedPurchase, setExtendedPurchase,
        reset,
      }}>
      {children}
    </AppContext.Provider>
  );
};
