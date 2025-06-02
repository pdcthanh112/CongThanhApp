'use client';

import { useCallback, useEffect, useState } from 'react';
import InputNumber, { InputNumberProps } from './InputNumber';
import { MinusIcon, PlusIcon } from '@/assets/icons';
import useDebounce from '@/hooks/useDebounce';

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
  const [localValue, setLocalValue] = useState<number>(Number(value || 1));
  const [classNameError, setClassNameError] = useState('hidden');

  const debouncedValue = useDebounce(localValue, 500);

  useEffect(() => {
    setLocalValue(Number(value || 0));
  }, [value]);

  // useEffect(() => {
  //   if (debouncedValue !== value) {
  //     if (debouncedValue > value) {
  //       onIncrease && onIncrease(debouncedValue);
  //     } else if (debouncedValue < value) {
  //       onDecrease && onDecrease(debouncedValue);
  //     }
  //   }
  // }, [debouncedValue, value, onIncrease, onDecrease]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setClassNameError('hidden');
    let _value = Number(event.target.value);
    if (max !== undefined && _value > max) {
      _value = max;
    } else if (min !== undefined && _value < min) {
      _value = min;
    }
    onType && onType(_value);
    setLocalValue(_value);
  };

  const increase = useCallback(() => {
    setClassNameError('hidden');
    let _value = Number(value || localValue) + 1;
    if (max !== undefined && _value > max) {
      _value = max;
    }
    onIncrease && onIncrease(_value);
    setLocalValue(_value);
    console.log('first', _value)
  }, [debouncedValue]);

  const decrease = useCallback(() => {
    setClassNameError('hidden');
    let _value = Number(value || localValue) - 1;
    if (_value < min) {
      _value = min;
    }
    onDecrease && onDecrease(_value);
    setLocalValue(_value);
  }, []);

  const handleBlur = (event: React.FocusEvent<HTMLInputElement, Element>) => {
    let _value = Number(event.target.value) || min;
    if ((max !== undefined && _value > max) || (min !== undefined && value < min)) {
      setClassNameError('mt-1 text-red-600 min-h-[1.25rem] text-sm');
    }
    onFocusOut && onFocusOut(_value);
    setLocalValue(_value);
  };

  return (
    <div className={'flex items-center'}>
      <button
        className="p-2 bg-[#f3f3f3] flex h-8 w-8 items-center justify-center rounded-l-sm border border-gray-300 text-gray-600 cursor-pointer"
        onClick={decrease}
        disabled={value <= min}
        aria-label="Decrease quantity"
        aria-controls="quantity-input"
      >
        <MinusIcon />
      </button>
      <InputNumber
        className=""
        classNameError={classNameError}
        classNameInput="h-8 w-14 border-t border-b border-gray-300 p-1 text-center outline-hidden"
        onChange={handleChange}
        onBlur={handleBlur}
        value={value || localValue}
        {...rest}
      />
      <button
        className="p-2 bg-[#f3f3f3] flex h-8 w-8 items-center justify-center rounded-r-sm border border-gray-300 text-gray-600 cursor-pointer"
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
