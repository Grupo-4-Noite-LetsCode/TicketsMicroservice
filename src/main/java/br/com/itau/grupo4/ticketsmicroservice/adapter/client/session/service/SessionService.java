package br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.service;

import br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto.SessionRequest;
import br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto.SessionResponse;
import br.com.itau.grupo4.ticketsmicroservice.exception.ExpiredSessionException;
import br.com.itau.grupo4.ticketsmicroservice.exception.SeatUnavailableException;
import br.com.itau.grupo4.ticketsmicroservice.exception.SessionNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class SessionService {

    private final WebClient webClient;

    public SessionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8070/sessions")
                .build();
    }

    public Mono<SessionResponse> unblockSeat(SessionRequest request) {
        Mono<SessionResponse> sessionResponseMono = this.webClient
                .patch()
                .uri("/unblock-seat")
                .body(Mono.just(request), SessionRequest.class)
                .retrieve()
                .bodyToMono(SessionResponse.class);
        return sessionResponseMono;
    }

    //TODO: Verificar os responses em sessions e confirmar como validar
    public void verifySessionIsAvailable(String sessionId) {
        SessionResponse sessionResponse = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}")
                        .build(sessionId))
                .retrieve()
                .bodyToMono(SessionResponse.class)
                .block();


        if (sessionResponse == null) {
            throw new SessionNotFoundException("A sessão informada não foi encontrada!");
        }

        if (sessionResponse.getDateTime().isBefore(LocalDateTime.now())) {
            throw new ExpiredSessionException("Essa sessão já foi iniciada, escolha um novo horário!");
        }
    }

    public void verifySeatIsAvailable(String sessionId, int column, int line) {
        SessionResponse sessionResponse = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}/isSeatOccupied/")
                        .queryParam("column", column)
                        .queryParam("line", line)
                        .build(sessionId))
                .retrieve()
                .bodyToMono(SessionResponse.class)
                .block();

        if (sessionResponse == null) {
            throw new SeatUnavailableException("O assento informado não está disponível, tente novamente!");
        }
    }

    public void ocupySeat(String sessionId, int column, int line) {
        SessionResponse sessionResponse = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}/occupySeat/")
                        .queryParam("column", column)
                        .queryParam("line", line)
                        .build(sessionId))
                .retrieve()
                .bodyToMono(SessionResponse.class)
                .block();

        if (sessionResponse == null) {
            throw new RuntimeException("Falha ao tentar reservar assento, tente novamente!");
        }
    }
}
