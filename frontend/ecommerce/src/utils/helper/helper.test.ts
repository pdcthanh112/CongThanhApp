import { roundNumber } from './';

describe('roundNumber', () => {
  it('round input number', () => {
    expect(roundNumber(123)).toBe('123');
    expect(roundNumber(1000.23)).toBe('1000.2k');
    expect(roundNumber(12345.678)).toBe('12345.7k');
  });
});
