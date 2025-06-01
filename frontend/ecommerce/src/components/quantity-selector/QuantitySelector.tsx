import { useEffect, useState } from 'react';
import InputNumber, { InputNumberProps } from './InputNumber';
import { MinusIcon, PlusIcon } from '@/assets/icons';

interface PropsType extends InputNumberProps {
  value: number;
  min?: number;
  max?: number;
  onIncrease?: (value: number) => void;
  onDecrease?: (value: number) => void;
  onType?: (value: number) => void;
  onFocusOut?: (value: number) => void;
}

const QuantitySelector = ({ min = 1, max, onIncrease, onDecrease, onType, onFocusOut, value, ...rest }: PropsType) => {
  const [localValue, setLocalValue] = useState<number>(Number(value || 0));

  useEffect(() => {
    setLocalValue(Number(value || 0));
  }, [value]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    let _value = Number(event.target.value);
    if (max !== undefined && _value > max) {
      _value = max;
    } else if (min !== undefined && _value < min) {
      _value = min;
    }
    onType && onType(_value);
    setLocalValue(_value);
  };

  const increase = () => {
    let _value = Number(value || localValue) + 1;
    if (max !== undefined && _value > max) {
      _value = max;
    }
    onIncrease && onIncrease(_value);
    setLocalValue(_value);
  };

  const decrease = () => {
    let _value = Number(value || localValue) - 1;
    if (_value < min) {
      _value = min;
    }
    onDecrease && onDecrease(_value);
    setLocalValue(_value);
  };

  const handleBlur = (event: React.FocusEvent<HTMLInputElement, Element>) => {
    onFocusOut && onFocusOut(Number(event.target.value));
  };

  return (
    <div className={'flex items-center'}>
      <button
        className="p-2 bg-[#f3f3f3] flex h-8 w-8 items-center justify-center rounded-l-sm border border-gray-300 text-gray-600"
        onClick={decrease}
        disabled={value <= min}
        aria-label="Decrease quantity"
        aria-controls="quantity-input"
      >
        <MinusIcon />
      </button>
      <InputNumber
        className=""
        classNameError="hidden"
        classNameInput="h-8 w-14 border-t border-b border-gray-300 p-1 text-center outline-hidden"
        onChange={handleChange}
        onBlur={handleBlur}
        value={value || localValue}
        {...rest}
      />
      <button
        className="p-2 bg-[#f3f3f3] flex h-8 w-8 items-center justify-center rounded-r-sm border border-gray-300 text-gray-600"
        onClick={increase}
        disabled={value >= max!}
        aria-label="Increase quantity"
        aria-controls="quantity-input"
      >
        <PlusIcon />
      </button>
    </div>
  );
};

export default QuantitySelector;
