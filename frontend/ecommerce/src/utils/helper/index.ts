import GoogleLoginImage from '@/assets/images/login-google-image.png';
import FacebookLoginImage from '@/assets/images/login-facebook-image.png';
import TwitterLoginImage from '@/assets/images/login-twitter-image.png';
import AppleLoginImage from '@/assets/images/login-apple-image.png';

export const roundNumber = (value: number) => {
  return new Intl.NumberFormat('en-US', {
    notation: 'compact',
    compactDisplay: 'short',
    maximumFractionDigits: 1,
  }).format(value);
};

export const getAuthLogo = (name: string) => {
  switch (name) {
    case 'google':
      return { img: GoogleLoginImage, bgColor: '#3b82f6', iconBg: '#fff' };
    case 'facebook':
      return { img: FacebookLoginImage, bgColor: '#1977d2', iconBg: '#1977d2' };
    case 'twitter':
      return { img: TwitterLoginImage, bgColor: '#04a9f3', iconBg: '#04a9f3' };
    case 'apple':
      return { img: AppleLoginImage, bgColor: '#878787', iconBg: '#878787' };
    default:
      return { img: '', bgColor: '#fdafdsa', iconBg: '#fff' };
  }
};

export const formatCurrency = (number: number, locale?: string, currency?: string) => {
  const formatter = new Intl.NumberFormat(locale ?? 'en-US', {
    style: 'currency',
    currency: currency ?? 'USD',
    maximumFractionDigits: 0,
    minimumFractionDigits: 0,
  });

  return formatter.format(number);
};

export function getSupplierUrl(slug: string, path: string = '') {
  if (typeof window !== 'undefined') {
    const host = window.location.host;
    const protocol = window.location.protocol;
    return `${protocol}//${slug}.${host.split('.').slice(-2).join('.')}${path}`;
  }
  return `https://${slug}.mydomain.com${path}`;
}
