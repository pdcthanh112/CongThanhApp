import { getSupplierBySlug } from '@/api/supplierApi';

async function checkSupplierExists(slug: string) {
  return await getSupplierBySlug(slug);
}

export default async function SupplierPage({ params }: { params: { slug: string; locale: string; path: string[] } }) {
  const { slug, locale, path } = params;

  return (
    <div>
      <h1>Supplier: {slug}</h1>
      <p>Locale: {locale}</p>
      <p>Path: /{path?.join('/') || ''}</p>
    </div>
  );
}
