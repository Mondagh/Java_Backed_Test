package services;

import java.math.BigDecimal;
import java.util.Random;

import model.PaymentResult;
import model.enums.PaymentStatus;

public class MockPaymentGatewayImp implements MockPaymentGateway {

	private final Random random = new Random();

	@Override
	public PaymentResult processPayment(String cardToken, BigDecimal amount) {

		boolean success = random.nextBoolean();

		if (success) {
			return new PaymentResult(PaymentStatus.PAID, "Payment successful");
		} else {
			return new PaymentResult(PaymentStatus.FAILED, "Card declined");
		}
	}

}
