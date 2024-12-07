package com.rama.backend_spring_boot.service;

import com.rama.backend_spring_boot.model.Payment;
import com.rama.backend_spring_boot.model.PaymentInput;
import com.rama.backend_spring_boot.model.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private final List<Payment> payments = new ArrayList<>();

    public Payment getPaymentById(String paymentId) {
        return payments.stream()
                .filter(payment -> payment.getPaymentId().equals(paymentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<Payment> getAllPayments() {
        return payments;
    }

    public PaymentResponse processPayment(PaymentInput input) {
        // Simulate payment processing
        Payment payment = new Payment();
        payment.setPaymentId("PAY-" + (payments.size() + 1));
        payment.setAmount(input.amount());
        payment.setCurrency(input.currency());
        payment.setStatus("SUCCESS");

        payments.add(payment);

        return new PaymentResponse(true, "Payment processed successfully", payment);
    }
}

