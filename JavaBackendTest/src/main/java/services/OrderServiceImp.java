package services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import Repositorys.OrderRepository;
import Repositorys.ProductRepository;
import controllers.FinishOrderRequest;
import model.Order;
import model.PaymentResult;
import model.Product;
import model.enums.OrderStatus;
import model.enums.PaymentStatus;

@Service
public class OrderServiceImp implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final MockPaymentGateway mockPaymentGateway;

	public OrderServiceImp(OrderRepository orderRepositori, ProductRepository productRepository,
			MockPaymentGateway mockPaymentGateway) {
		this.orderRepository = orderRepositori;
		this.productRepository = productRepository;
		this.mockPaymentGateway = mockPaymentGateway;
	}

	@Override
	public void putOrder(Order order) {
		// look if exist
		Optional<Order> originalOrder = orderRepository.findById(order.getId());
		if (originalOrder.isPresent()) {
			// updating
			Order updatingOrder = originalOrder.get();
			// calculate stock and price

			Map<Long, Integer> updating = ListToMap(updatingOrder.getProducts());
			Map<Long, Integer> newOrder = ListToMap(order.getProducts());
			BigDecimal totalPrice = updatingOrder.getPayment().getTotalPrice();

			Set<Long> allIds = new HashSet<>();
			allIds.addAll(updating.keySet());
			allIds.addAll(newOrder.keySet());

			for (Long idProduct : allIds) {
				int cuantityUpdating = updating.getOrDefault(idProduct, 0);
				int cuantityNew = newOrder.getOrDefault(idProduct, 0);
				int diference = cuantityNew - cuantityUpdating;

				Optional<Product> productInDb = productRepository.findById(idProduct);
				if (productInDb.isPresent()) {
					Product product = productInDb.get();

					BigDecimal priceToCalculate = product.getPrice().multiply(BigDecimal.valueOf(Math.abs(diference)));

					if (diference > 0) {
						// add new products
						totalPrice.add(priceToCalculate);
						product.setStock(product.getStock() - diference);
					} else if (diference < 0) {
						// delete products

						totalPrice.subtract(priceToCalculate);

						product.setStock(product.getStock() + Math.abs(diference));
					}

				}

			}

			updatingOrder.getPayment().setTotalPrice(totalPrice);
			updatingOrder.setProducts(order.getProducts());
			updatingOrder.setPayment(order.getPayment());
			updatingOrder.setBuyer(order.getBuyer());

		} else {
			// new order
			for (Product product : order.getProducts()) {
				Product productInDb = productRepository.findById(product.getId())
						.orElseThrow(() -> new RuntimeException("Product not found"));

				if (productInDb.getStock() > 0) {
					productInDb.setStock(productInDb.getStock() - 1);
				} else {
					// throw
					throw new RuntimeException("Not enough stock for product: " + productInDb.getName());
				}

			}
			order.setStatus(OrderStatus.OPEN);
			orderRepository.save(order);
		}

	}

	@Override
	public void cancelOrder(Long orderId) {
		Optional<Order> orderInDb = orderRepository.findById(orderId);
		if (orderInDb.isPresent()) {
			Order order = orderInDb.get();
			order.setStatus(OrderStatus.CANCELED);
		}

	}

	@Override
	public void finishOrder(Long orderId, FinishOrderRequest request) {

		Optional<Order> orderInDb = orderRepository.findById(orderId);
		if (orderInDb.isPresent()) {
			Order order = orderInDb.get();
			PaymentResult result = mockPaymentGateway.processPayment(request.getCardToken(), order.getPayment().getTotalPrice());

			order.getPayment().setStatus(result.getStatus());
			order.getPayment().setPaymentDate(LocalDateTime.now());
			order.getPayment().setPaymentGateway(request.getPaymentGateway());
			if (result.getStatus() == PaymentStatus.PAID) {
				order.setStatus(OrderStatus.FINISHED);
			}

			orderRepository.save(order);

		}

	}

	/**
	 * 
	 * @param products
	 * @return
	 */
	private Map<Long, Integer> ListToMap(List<Product> products) {
		Map<Long, Integer> map = new HashMap<>();
		for (Product p : products) {
			map.put(p.getId(), map.getOrDefault(p.getId(), 0) + 1);
		}
		return map;
	}

}
