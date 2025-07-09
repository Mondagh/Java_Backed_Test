package services;

import java.math.BigDecimal;

import model.PaymentResult;

public interface MockPaymentGateway {
	PaymentResult processPayment(String cardToken, BigDecimal amount);

}
