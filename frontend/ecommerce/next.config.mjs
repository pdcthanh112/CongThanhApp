import createNextIntlPlugin from 'next-intl/plugin';

const withNextIntl = createNextIntlPlugin();
// const withNextIntl = createNextIntlPlugin('./src/app/i18n.ts');

/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  // experimental: {
  //   dynamicIO: true,
  //   cacheLife: {
  //     getAllProduct: {
  //       stale: undefined, // No stale data
  //       revalidate: 1 * 60, // Revalidate every 1 minute
  //       expire: 15 * 60, // Expire after 15 minute
  //     },
  //     dailySummary: {
  //       stale: 5,
  //       revalidate: 30,
  //       expire: 120,
  //     },
  //     historicalReports: {
  //       stale: 30, // Keep stale for 30 seconds
  //       revalidate: 60,
  //       expire: 600,
  //     },
  //   },
  // },
  async redirects() {
    return [
      {
        source: '/',
        destination: '/home',
        permanent: true,
      },
    ];
  },

  eslint: {
    ignoreDuringBuilds: true,
  },
  logging: {
    fetches: {
      fullUrl: true,
    },
  },
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: '*',
      },
    ],
  },
};

export default withNextIntl(nextConfig);
