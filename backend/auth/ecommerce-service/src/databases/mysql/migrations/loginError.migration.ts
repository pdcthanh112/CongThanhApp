'use strict';
module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.createTable('LoginError', {
      id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        primaryKey: true,
      },
      accountId: {
        allowNull: false,
        unique: true,
        type: Sequelize.STRING(45),
      },
      failedAttempts: {
        allowNull: false,
        type: Sequelize.INTEGER,
      },
      lockedUntil: {
        allowNull: false,
        type: Sequelize.DATE,
      },
    });
  },
  down: async (queryInterface, Sequelize) => {
    await queryInterface.dropTable('LoginError');
  },
};
