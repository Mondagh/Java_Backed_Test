package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.validation.Valid;
import model.Order;
import services.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {
	
	

		@Autowired
		private OrderService service;
		
		
		@PostMapping
		public void putOrder( @Valid @RequestBody Order order){
			 service.putOrder(order);
		}
		
		@PostMapping("/{id}/finish")
		public void finishOrder(@PathVariable Long id, @RequestBody FinishOrderRequest request) {
			service.finishOrder(id, request);
		}
		
		@PostMapping("/{id}/cancel")
		public void cancelOrder(@PathVariable Long id) {
			service.cancelOrder(id);
		}

}
