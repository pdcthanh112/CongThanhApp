import { Product } from '@/models/types';
import ProductItemCard from './ProductItemCard';
import ProductItemCardSkeleton from './ProductItemCard/ProductItemCardSkeleton';

const dummyData = [
  {
    id: 'lkaslfjldsfjaslks',
    name: 'AAAAAAAAAAAAA',
    category: 1412423,
    slug: 'adfasddf',
    description: 'adladsjljladsf',
    supplier: 'string',
    image: [
      {
        id: 1,
        product: 'lkaslfjldsfjaslks',
        imagePath: 'https://images.stockcake.com/public/7/3/7/7375ee9e-ecee-484f-9f33-7a9a55d079ce_large/sunny-daisy-field-stockcake.jpg',
        alt: 'lkaslfjldsfjaslks',
        isDefault: true,
      },
      {
        id: 2,
        product: 'lkaslfjldsfjaslks',
        imagePath: 'https://images.stockcake.com/public/7/3/7/7375ee9e-ecee-484f-9f33-7a9a55d079ce_large/sunny-daisy-field-stockcake.jpg',
        alt: 'lkaslfjldsfjaslks',
        isDefault: false,
      },
      {
        id: 3,
        product: 'lkaslfjldsfjaslks',
        imagePath: 'https://images.stockcake.com/public/7/3/7/7375ee9e-ecee-484f-9f33-7a9a55d079ce_large/sunny-daisy-field-stockcake.jpg',
        alt: 'lkaslfjldsfjaslks',
        isDefault: false,
      },
    ],
    brand: 'string',
    status: 'string',
    variant: null,
    attribute: null,
  }
]

type ShowListProductProps = {
  listProduct: Product[];
  loading: boolean;
};

export default function ShowListProduct({ listProduct, loading = true }: ShowListProductProps) {
  if (loading)
    return (
      <div className="w-[90%] mx-auto grid gap-4 grid-flow-row-dense grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
        {Array.from(Array(10)).map((_, id) => (
          <ProductItemCardSkeleton key={id} />
        ))}
      </div>
    );

  return (
    <div className="grid gap-4 grid-flow-row-dense grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
      {dummyData?.map((product: Product) => (
        <ProductItemCard key={product.id} product={product} />
      ))}
    </div>
  );
}
