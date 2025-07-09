package model;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import model.enums.OrderStatus;



public class Order {
	
	private List<Product> products;
	private Payment payment;
	private OrderStatus status;
	@NotNull
	private String seat;

}
