package Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Product;

public interface ProductRepository  extends JpaRepository<Product, Long> {}


