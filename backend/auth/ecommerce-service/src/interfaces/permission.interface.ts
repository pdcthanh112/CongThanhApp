export interface Permission {
  id: string;
  name: string;
  description: string;
  action: string; // create, read, update, delete
  resource: string; // users, products, orders, etc.
}
