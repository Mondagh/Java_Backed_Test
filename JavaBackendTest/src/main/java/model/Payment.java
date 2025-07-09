package model;

import java.math.BigDecimal;
import java.util.Date;

import model.enums.PaymentStatus;

public class Payment {

	private BigDecimal totalPrice;
	private String cardToken;
	private PaymentStatus status;
	private Date paymentDate;
	private String paymentGateway;
}
