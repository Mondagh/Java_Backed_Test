package services;

import model.Product;

public interface ProductService {

	public void putProduct(Product product);

	public void deleteProduct(Long productId);

}
