import { render, screen, fireEvent } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import QuantitySelector from './QuantitySelector';

describe('QuantitySelector', () => {
  const defaultProps = {
    value: 1,
    min: 1,
    max: 10,
    onIncrease: jest.fn(),
    onDecrease: jest.fn(),
    onType: jest.fn(),
    onFocusOut: jest.fn(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders correctly with default props', () => {
    render(<QuantitySelector {...defaultProps} />);

    expect(screen.getByRole('spinbutton', { name: /quantity/i })).toHaveValue(1);
    expect(screen.getByRole('button', { name: /decrease quantity/i })).toBeDisabled();
    expect(screen.getByRole('button', { name: /increase quantity/i })).not.toBeDisabled();
  });

  it('increases quantity when plus button is clicked', async () => {
    render(<QuantitySelector {...defaultProps} value={5} />);

    const plusButton = screen.getByRole('button', { name: /increase quantity/i });
    await userEvent.click(plusButton);

    expect(defaultProps.onIncrease).toHaveBeenCalledWith(6);
    expect(screen.getByRole('spinbutton')).toHaveValue(6);
  });

  it('does not increase quantity beyond max', async () => {
    render(<QuantitySelector {...defaultProps} value={10} />);

    const plusButton = screen.getByRole('button', { name: /increase quantity/i });
    expect(plusButton).toBeDisabled();
    await userEvent.click(plusButton);

    expect(defaultProps.onIncrease).not.toHaveBeenCalled();
    expect(screen.getByRole('spinbutton')).toHaveValue(10);
  });

  it('decreases quantity when minus button is clicked', async () => {
    render(<QuantitySelector {...defaultProps} value={5} />);

    const minusButton = screen.getByRole('button', { name: /decrease quantity/i });
    await userEvent.click(minusButton);

    expect(defaultProps.onDecrease).toHaveBeenCalledWith(4);
    expect(screen.getByRole('spinbutton')).toHaveValue(4);
  });

  it('does not decrease quantity below min', async () => {
    render(<QuantitySelector {...defaultProps} value={1} />);

    const minusButton = screen.getByRole('button', { name: /decrease quantity/i });
    expect(minusButton).toBeDisabled();
    await userEvent.click(minusButton);

    expect(defaultProps.onDecrease).not.toHaveBeenCalled();
    expect(screen.getByRole('spinbutton')).toHaveValue(1);
  });

  it('updates quantity when typing valid number', async () => {
    render(<QuantitySelector {...defaultProps} value={5} />);

    const input = screen.getByRole('spinbutton');
    await userEvent.clear(input);
    await userEvent.type(input, '7');

    expect(defaultProps.onType).toHaveBeenCalledWith(7);
    expect(input).toHaveValue(7);
  });

  it('clamps value to max when typing above max', async () => {
    render(<QuantitySelector {...defaultProps} value={5} />);

    const input = screen.getByRole('spinbutton');
    await userEvent.clear(input);
    await userEvent.type(input, '15');

    expect(defaultProps.onType).toHaveBeenCalledWith(10);
    expect(input).toHaveValue(10);
  });

  it('clamps value to min when typing below min', async () => {
    render(<QuantitySelector {...defaultProps} value={5} min={3} />);

    const input = screen.getByRole('spinbutton');
    await userEvent.clear(input);
    await userEvent.type(input, '2');

    expect(defaultProps.onType).toHaveBeenCalledWith(3);
    expect(input).toHaveValue(3);
  });

  it('handles empty input by setting to min', async () => {
    render(<QuantitySelector {...defaultProps} value={5} min={3} />);

    const input = screen.getByRole('spinbutton');
    await userEvent.clear(input);
    fireEvent.blur(input);

    expect(defaultProps.onFocusOut).toHaveBeenCalledWith(3);
    expect(input).toHaveValue(3);
  });

  it('calls onFocusOut when input loses focus', async () => {
    render(<QuantitySelector {...defaultProps} value={5} />);

    const input = screen.getByRole('spinbutton');
    await userEvent.type(input, '8');
    fireEvent.blur(input);

    expect(defaultProps.onFocusOut).toHaveBeenCalledWith(8);
  });

  it('ignores negative numbers and non-numeric input', async () => {
    render(<QuantitySelector {...defaultProps} value={5} />);

    const input = screen.getByRole('spinbutton');
    await userEvent.clear(input);
    await userEvent.type(input, '-5');

    expect(defaultProps.onType).not.toHaveBeenCalled();
    expect(input).toHaveValue(5);

    await userEvent.clear(input);
    await userEvent.type(input, 'abc');

    expect(defaultProps.onType).not.toHaveBeenCalled();
    expect(input).toHaveValue(5);
  });

  it('handles undefined min/max gracefully', () => {
    render(<QuantitySelector {...defaultProps} min={undefined} max={undefined} value={5} />);

    const minusButton = screen.getByRole('button', { name: /decrease quantity/i });
    const plusButton = screen.getByRole('button', { name: /increase quantity/i });

    expect(minusButton).not.toBeDisabled();
    expect(plusButton).not.toBeDisabled();
  });

  it('handles zero or invalid initial value', () => {
    render(<QuantitySelector {...defaultProps} value={0} />);

    expect(screen.getByRole('spinbutton')).toHaveValue(1);
  });

  it('has correct ARIA attributes', () => {
    render(<QuantitySelector {...defaultProps} />);

    const minusButton = screen.getByRole('button', { name: /decrease quantity/i });
    const plusButton = screen.getByRole('button', { name: /increase quantity/i });
    const input = screen.getByRole('spinbutton');

    expect(minusButton).toHaveAttribute('aria-controls', input.id);
    expect(plusButton).toHaveAttribute('aria-controls', input.id);
    expect(input).toHaveAttribute('aria-label', 'Quantity');
  });

  it('disables buttons correctly at boundaries', () => {
    render(<QuantitySelector {...defaultProps} value={1} min={1} max={10} />);

    expect(screen.getByRole('button', { name: /decrease quantity/i })).toBeDisabled();
    expect(screen.getByRole('button', { name: /increase quantity/i })).not.toBeDisabled();

    render(<QuantitySelector {...defaultProps} value={10} min={1} max={10} />);

    expect(screen.getByRole('button', { name: /decrease quantity/i })).not.toBeDisabled();
    expect(screen.getByRole('button', { name: /increase quantity/i })).toBeDisabled();
  });

  it('passes additional props to InputNumber', () => {
    render(<QuantitySelector {...defaultProps} errorMessage="Error" />);

    expect(screen.getByText('Error')).toBeInTheDocument();
  });
});
