// import { Sequelize, DataTypes, Model, Optional } from 'sequelize';
// import { Account } from '@interfaces/account.interface';

// //export type UserCreationAttributes = Optional<Account | 'id' | 'email' | 'password'>;

// export class AccountModel extends Model<Account> implements Account{
//   public id: number;
//   public email: string;
//   public password: string;

//   public readonly createdAt!: Date;
//   public readonly updatedAt!: Date;
// }

// export default function (sequelize: Sequelize): typeof AccountModel {
//   AccountModel.init(
//     {
//       id: {
//         type: DataTypes.UUID,
//         defaultValue: DataTypes.UUIDV4,
//         primaryKey: true,
//       },
//       email: {
//         allowNull: false,
//         type: DataTypes.STRING(45),
//       },
//       password: {
//         allowNull: false,
//         type: DataTypes.STRING(255),
//       },
//     },
//     {
//       tableName: 'Account',
//       sequelize,
//     },
//   );

//   return AccountModel;
// }
