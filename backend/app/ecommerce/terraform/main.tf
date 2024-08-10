module "networking" {
  source = "./modules/networking"
  # Truyền các biến cần thiết
}

module "databases" {
  source = "./modules/databases"
  # Truyền các biến cần thiết
}

module "kubernetes" {
  source = "./modules/kubernetes"
  # Truyền các biến cần thiết
}

# Định nghĩa các resource khác nếu cần