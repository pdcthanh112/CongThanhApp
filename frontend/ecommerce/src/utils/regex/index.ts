export const lowerCharacterRegex = new RegExp(/(?=.*[a-z])/);
export const upperCharacterRegex = new RegExp(/(?=.*[A-Z])/);
export const numberCharacterRegex = new RegExp(/(?=.*[0-9])/);
export const specialCharacterRegex = new RegExp(/(?=.*[!@#$%^&*+-<>=])/);
export const lengthRegex = new RegExp(/^(?=.{8,32}$)/);