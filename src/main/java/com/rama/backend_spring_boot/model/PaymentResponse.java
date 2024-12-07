package com.rama.backend_spring_boot.model;

public class PaymentResponse {
    private boolean success;
    private String message;
    private Payment payment;

    public PaymentResponse(boolean success, String message, Payment payment) {
        this.success = success;
        this.message = message;
        this.payment = payment;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Payment getPayment() { return payment; }
}

