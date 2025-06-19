CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_small_id INT NOT NULL,
  manufacturer_id INT NOT NULL,
  name VARCHAR(255) NOT NULL UNIQUE,
  description TEXT,
  image_url VARCHAR(255),
  cost_price DECIMAL(10,2),
  retail_price DECIMAL(10,2),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (category_small_id) REFERENCES categories_small(id),
  FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id)
);