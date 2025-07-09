package model;

import model.enums.PaymentStatus;

public class PaymentResult {
    private PaymentStatus status;
    private String message;

    public PaymentResult(PaymentStatus status, String message) {
        this.status = status;
        this.message = message;
    }

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

   
}
