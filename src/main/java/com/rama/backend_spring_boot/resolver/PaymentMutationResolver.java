package com.rama.backend_spring_boot.resolver;

import com.rama.backend_spring_boot.model.PaymentInput;
import com.rama.backend_spring_boot.model.PaymentResponse;
import com.rama.backend_spring_boot.service.PaymentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentMutationResolver {

    private final PaymentService paymentService;

    public PaymentMutationResolver(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @MutationMapping
    public PaymentResponse processPayment(@Argument PaymentInput input) {
        return paymentService.processPayment(input);
    }
}

