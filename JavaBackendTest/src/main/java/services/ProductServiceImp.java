package services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import Repositorys.ProductRepository;
import model.Product;

@Service
public class ProductServiceImp implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImp(ProductRepository productRepository) {

		this.productRepository = productRepository;
	}

	@Override
	public void putProduct(Product product) {
		productRepository.save(product);

	}

	@Override
	public void deleteProduct(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			productRepository.delete(product.get());
		}
	}

}
