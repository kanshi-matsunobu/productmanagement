CREATE TABLE IF NOT EXISTS product_orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  admin_id INT NOT NULL,
  store_id INT NOT NULL,
  product_id INT NOT NULL,
  order_quantity INT NOT NULL CHECK (order_quantity > 0),
  total_price DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (admin_id) REFERENCES admins(id),
  FOREIGN KEY (store_id) REFERENCES stores(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);