package br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.service;

import br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto.SessionRequest;
import br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto.SessionResponse;
import br.com.itau.grupo4.ticketsmicroservice.exception.SessionNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class SessionService {

    private final WebClient webClient;

    public SessionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8070/sessions")
                .build();
    }

    public Mono<SessionResponse> unblockSeat(SessionRequest request){
        Mono<SessionResponse> sessionResponseMono = this.webClient
                .patch()
                .uri("/unblock-seat")
                .body(Mono.just(request), SessionRequest.class)
                .retrieve()
                .bodyToMono(SessionResponse.class);
        return sessionResponseMono;
    }

    public void verifySessionIsAvailable(String sessionId) {
        Mono<SessionResponse> sessionResponseMono = this.webClient
                .get()
                .uri("/"+sessionId)
                .retrieve()
                .bodyToMono(SessionResponse.class);

        if (sessionResponseMono.getClass() == null) {
            throw new SessionNotFoundException("A sessão informada não foi encontrada!");
        }
    }
}
