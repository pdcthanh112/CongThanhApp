"use client";
import { Inter } from "next/font/google";
import "../globals.css";
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import AppNavbar from "@/components/AppNavbar";
import RootLayout from "@/layout/RootLayout";
import Providers from "./providers";

const inter = Inter({ subsets: ["latin"] });

type LayoutProps = {
  children: React.ReactNode;
};

// export const metadata: Metadata = {
//   title: "Online Store",
//   description: "The best online store on the internet.",
//   openGraph: {
//     title: "Online Store",
//     description: "The best online store on the internet.",
//     url: "https://example.com",
//     siteName: "Online Store",
//     type: "website",
//     images: [
//       {
//         url: "https://example.com/og-img.jpg",
//       },
//     ],
//   },
// };

export default function Layout({ children }: Readonly<LayoutProps>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body className={inter.className}>
        <Providers>
          <AppHeader />
          <AppNavbar />
          <RootLayout>
            <main className="min-h-[calc(100vh-270px)]">{children}</main>
          </RootLayout>
          <AppFooter />
        </Providers>
      </body>
    </html>
  );
}

// interface MetadataProps extends Metadata {
//   title: string;
//   keywords: string;
//   description: string;
//   url: string;
//   thumbnailUrl: string;
// }

// export const CustomMeta = ({ title, keywords, description, url, thumbnailUrl }: MetadataProps) => {
//   return (
//     <Head>
//       <title>{title}</title>
//       <meta name="title" content="CongThanh-Ecommerce" />
//       <meta name="description" content="Ecommerce site for CongThanh-project app" />

//       <meta property="og:type" content="website" />
//       <meta property="og:url" content={url} />
//       <meta property="og:title" content={title} />
//       <meta property="og:description" content={description} />
//       <meta property="og:image" content={thumbnailUrl} />
//       <meta property="og:keywords" content={keywords} />

//       <meta property="twitter:card" content="summary_large_image" />
//       <meta property="twitter:url" content={url} />
//       <meta property="twitter:title" content={title} />
//       <meta property="twitter:description" content={description} />
//       <meta property="twitter:image" content={thumbnailUrl} />
//       <meta property="twitter:keywords" content={keywords} />
//     </Head>
//   );
// };

// CustomMeta.defaultProps = {
//   title: "CongThanh E-commerce App",
//   keywords: "Ecommmerce App, NextJS, Amazon, Shopping",
//   description: "Ecommerce site of CongThanh-project App",
//   url: "my-dream-8uz66imd5-pdcthanh112.vercel.app",
//   thumbnailUrl: "thumbnailUrl",
// };
