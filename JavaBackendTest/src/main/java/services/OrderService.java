package services;

import model.Order;



public interface OrderService {

	public void putOrder(Order order);
	public void cancelOrder(Long orderId);
	public void finishOrder(Long orderId, String cardToken, String paymentGatewayName);

}
