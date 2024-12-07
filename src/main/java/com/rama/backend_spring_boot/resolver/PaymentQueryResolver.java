package com.rama.backend_spring_boot.resolver;

import com.rama.backend_spring_boot.model.Payment;
import com.rama.backend_spring_boot.service.PaymentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PaymentQueryResolver {

    private final PaymentService paymentService;

    public PaymentQueryResolver(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @QueryMapping
    public Payment getPayment(@Argument String paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @QueryMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}


