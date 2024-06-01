// const path = require('path');
module.exports = {
  debug: process.env.NODE_ENV === 'development',
  i18n: {
    defaultLocale: 'en',
    locales: ['en', 'vi', 'zh', 'es', 'de', 'fr', 'it', 'ko', 'ru'],
    // localePath: path.resolve('./public/locales'),
  },
  reloadOnPrerender: process.env.NODE_ENV === 'development',
};

