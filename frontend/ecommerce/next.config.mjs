/** @type {import('next').NextConfig} */
const nextConfig = {
    reactStrictMode: true,
    async redirects() {
        return [
          {
            source: '/',
            destination: '/home',
            permanent: true,
          },
        ]
      },
    eslint: {
        ignoreDuringBuilds: true
    },
    logging: {
        fetches: {
          fullUrl: true,
        },
      },
};

export default nextConfig;
