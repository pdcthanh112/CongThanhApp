import { render, screen, fireEvent } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import ProductItemCard from '../ProductItemCard';
import { Product } from '@/models/types';

const product: Product = {
  id: '123',
  name: 'Product 1',
  category: [],
  slug: 'product-slug',
  description: 'product-description',
  supplier: '',
  thumbnail: {
    id: 1,
    product: '',
    imagePath: '',
  },
  image: [{ id: 1, product: '', imagePath: '' }],
  parent: '',
  hasVariant: false,
  brand: 'brand',
  status: '',
  variant: [],
  attribute: [],
  metadata: {
    title: 'product-title',
    keywords: 'product-keyword',
    description: 'description',
  },
};

const addToCartMock = jest.fn();

describe('ProductCard', () => {
  it('renders product details correctly', () => {
    render(<ProductItemCard product={product} />);

    expect(screen.getByText('Product 1')).toBeInTheDocument();
    expect(screen.getByText('$100')).toBeInTheDocument();
    expect(screen.getByTestId('add-to-cart')).toBeInTheDocument();
  });

  it('calls addToCart when button is clicked', () => {
    render(<ProductItemCard product={product} />);

    fireEvent.click(screen.getByTestId('add-to-cart'));
    expect(addToCartMock).toHaveBeenCalledWith(product);
  });
});

describe('AddTodo', () => {
  describe('Render', () => {
    it('should render an article', () => {
      render(<ProductItemCard product={product} setTodos={mockSetTodos} />);

      //ACT
      const article = screen.getByRole('article');

      expect(article).toBeInTheDocument(); // ASSERT
    });

    it('should render a label', () => {
      render(<ProductItemCard product={mockTodo} setTodos={mockSetTodos} />); // ARRANGE

      //ACT
      const label = screen.getByTestId('todo-item');

      expect(label).toBeInTheDocument(); // ASSERT
    });

    it('should render a checkbox', () => {
      render(<ProductItemCard product={mockTodo} setTodos={mockSetTodos} />); // ARRANGE

      //ACT
      const checkbox = screen.getByRole('checkbox');

      expect(checkbox).toBeInTheDocument(); // ASSERT
    });

    it('should render a button', () => {
      render(<ProductItemCard product={mockTodo} setTodos={mockSetTodos} />); // ARRANGE

      //ACT
      const button = screen.getByRole('button');

      expect(button).toBeInTheDocument(); // ASSERT
    });
  });

  describe('Behavior', () => {
    it('should call setTodos when checkbox clicked', async () => {
      render(<ProductItemCard todo={mockTodo} setTodos={mockSetTodos} />); // ARRANGE

      //ACT
      const checkbox = screen.getByRole('checkbox');
      await userEvent.click(checkbox);

      expect(mockSetTodos).toBeCalled(); // ASSERT
    });

    it('should call setTodos when button clicked', async () => {
      render(<ProductItemCard todo={mockTodo} setTodos={mockSetTodos} />); // ARRANGE

      //ACT
      const button = screen.getByRole('button');
      await userEvent.click(button);

      expect(mockSetTodos).toBeCalled(); // ASSERT
    });
  });
});
