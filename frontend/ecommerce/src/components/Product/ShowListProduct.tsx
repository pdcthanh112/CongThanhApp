import { Product } from '@models/type';
import ProductItemCard from './ProductItemCard';
import ProductItemCardSkeleton from './ProductItemCard/ProductItemCardSkeleton';

interface ShowListProductProps {
  listProduct: Product[];
  loading: boolean;
}
const ShowListProduct = ({ listProduct, loading }: ShowListProductProps): React.ReactElement => {
  if (loading)
    return (
      <div className="w-[90%] mx-auto grid gap-4 grid-flow-row-dense grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
        {Array(10).map((_, id) => (
          <ProductItemCardSkeleton key={id} />
        ))}
      </div>
    );

  return (
    <div className="grid gap-4 grid-flow-row-dense grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
      {listProduct?.map((product: Product) => <ProductItemCard key={product.id} product={product} />)}
    </div>
  );
};

export default ShowListProduct;
