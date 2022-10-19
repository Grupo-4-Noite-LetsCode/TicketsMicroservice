package br.com.itau.grupo4.ticketsmicroservice.client.payment.service;

import br.com.itau.grupo4.ticketsmicroservice.client.payment.dto.RefundRequest;
import br.com.itau.grupo4.ticketsmicroservice.client.payment.dto.RefundResponse;
import br.com.itau.grupo4.ticketsmicroservice.client.session.dto.SessionRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaymentService {
    private final WebClient webClient;

    public PaymentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8090")
                .build();
    }

    public Mono<RefundResponse> patchReimbursement(RefundRequest request){
        Mono<RefundResponse> paymentoResponseMono = this.webClient
                .patch()
                .uri("/payment/reimbursement")
                .body(Mono.just(request), RefundRequest.class)
                .retrieve()
                .bodyToMono(RefundResponse.class);
        return paymentoResponseMono;
    }
}
