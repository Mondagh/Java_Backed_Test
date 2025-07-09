package model;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import model.enums.OrderStatus;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Order {
	
	@Id
	@NotNull
	private Long id;
	private List<Product> products;
	private Payment payment;
	private OrderStatus status;
	private Buyer buyer;


	
	
	public Order() {
		super();
		this.status= OrderStatus.OPEN;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public Buyer getBuyer() {
		return this.buyer;
	}
	
	public void setBuyer(Buyer buyer) {
		this.buyer=buyer;
	}
	

}
