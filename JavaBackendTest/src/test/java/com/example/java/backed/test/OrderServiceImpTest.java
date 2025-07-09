package com.example.java.backed.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import Repositorys.OrderRepository;
import Repositorys.ProductRepository;
import model.Order;
import model.Payment;
import model.Product;
import model.enums.OrderStatus;
import services.MockPaymentGateway;
import services.OrderServiceImp;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImpTest {
	
	 @Mock
	    private OrderRepository orderRepository;

	    @Mock
	    private ProductRepository productRepository;

	    @Mock
	    private MockPaymentGateway paymentGateway;

	    @InjectMocks
	    private OrderServiceImp orderService;

	    private Order order;
	    
	    @Test
	    void putOrder_shouldCreateNewOrder_andReduceStock() {
	        // Arrange
	        Product product = new Product();
	        product.setId(1L);
	        product.setName("Producte Test");
	        product.setPrice(new BigDecimal("10.00"));
	        product.setStock(5);

	        Payment payment = new Payment();
	        payment.setTotalPrice(new BigDecimal("10.00"));

	        Order newOrder = new Order();
	        newOrder.setId(100L);
	        newOrder.setProducts(List.of(product));
	        newOrder.setPayment(payment);

	        when(orderRepository.findById(100L)).thenReturn(Optional.empty());
	        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

	        // Act
	        orderService.putOrder(newOrder);

	        // Assert
	        assertEquals(4, product.getStock());
	        assertEquals(OrderStatus.OPEN, newOrder.getStatus());
	        verify(orderRepository).save(newOrder);
	    }
	    
	    @Test
	    void putOrder_shouldUpdateExistingOrder_andAdjustStockAndPrice() {
	        // Arrange
	        Product product = new Product();
	        product.setId(1L);
	        product.setName("Producte Actualitzat");
	        product.setPrice(new BigDecimal("20.00"));
	        product.setStock(10);

	        Product originalProduct = new Product();
	        originalProduct.setId(1L);
	        originalProduct.setName("Producte Actualitzat");
	        originalProduct.setPrice(new BigDecimal("20.00"));
	        originalProduct.setStock(10);

	        Payment payment = new Payment();
	        payment.setTotalPrice(new BigDecimal("20.00"));

	        Order existingOrder = new Order();
	        existingOrder.setId(200L);
	        existingOrder.setProducts(List.of(product));
	        existingOrder.setPayment(payment);

	        Order updatedOrder = new Order();
	        updatedOrder.setId(200L);
	        updatedOrder.setProducts(List.of(product, product)); 
	        updatedOrder.setPayment(new Payment());

	        when(orderRepository.findById(200L)).thenReturn(Optional.of(existingOrder));
	        when(productRepository.findById(1L)).thenReturn(Optional.of(originalProduct));

	        // Act
	        orderService.putOrder(updatedOrder);

	        // Assert
	        assertEquals(9, originalProduct.getStock()); // s'ha restat 1 unitat més
	        assertEquals(2, updatedOrder.getProducts().size());
	        assertEquals(existingOrder.getProducts().size(), updatedOrder.getProducts().size());
	    }



}
