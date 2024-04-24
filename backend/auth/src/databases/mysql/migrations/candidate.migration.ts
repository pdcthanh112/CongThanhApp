'use strict';
module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.createTable('Candidate', {
      id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        primaryKey: true,
      },
      email: {
        allowNull: false,
        type: Sequelize.STRING(45),
      },
      password: {
        allowNull: false,
        type: Sequelize.STRING(255),
      },
      accountId: {
        allowNull: false,
        unique: true,
        type: Sequelize.STRING(45),
      },
      name: {
        allowNull: false,
        type: Sequelize.STRING(45),
      },
      phone: {
        allowNull: false,
        type: Sequelize.STRING(20),
      },
      address: {
        allowNull: false,
        type: Sequelize.STRING,
      },
      dob: {
        type: Sequelize.DATEONLY,
      },
      gender: {
        allowNull: false,
        type: Sequelize.STRING(10),
      },
      image: {
        allowNull: false,
        type: Sequelize.STRING,
      },
    });
  },
  down: async (queryInterface, Sequelize) => {
    await queryInterface.dropTable('Candidate');
  }
};