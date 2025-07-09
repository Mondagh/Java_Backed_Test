package controllers;

import model.enums.PaymentStatus;

public class FinishOrderRequest {

	private PaymentStatus paymentStatus;
	private String cardToken;
	private String paymentGateway;

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public String getCardToken() {
		return cardToken;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

}
