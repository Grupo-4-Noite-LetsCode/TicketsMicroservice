package br.com.itau.grupo4.ticketsmicroservice.client.session.service;

import br.com.itau.grupo4.ticketsmicroservice.client.session.dto.SessionRequest;
import br.com.itau.grupo4.ticketsmicroservice.client.session.dto.SessionResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class SessionService {

    private final WebClient webClient;

    public SessionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8070")
                .build();
    }

    public Mono<SessionResponse> unblockSeat(SessionRequest request){
        Mono<SessionResponse> sessionResponseMono = this.webClient
                .patch()
                .uri("/session/unblock-seat")
                .body(Mono.just(request), SessionRequest.class)
                .retrieve()
                .bodyToMono(SessionResponse.class);
        return sessionResponseMono;
    }
}
