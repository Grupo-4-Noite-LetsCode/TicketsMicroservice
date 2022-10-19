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
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}")
                        .build(sessionId))
                .retrieve()
                .bodyToMono(SessionResponse.class);

        if (sessionResponseMono.blockOptional().isEmpty()) {
            throw new SessionNotFoundException("A sessão informada não foi encontrada!");
        }

        //TODO: confirmar se o response vem com a data e hora da sessão, e como vem (nomes das variáveis e tipos).
        if (sessionResponseMono.blockOptional().get().getDateTime().isBefore(LocalDateTime.now())){
            throw new ExpiredSessionException("Essa sessão já foi iniciada, escolha um novo horário!");
        }
    }

    public void verifySeatIsAvailable(String sessionId, int column, int line) {
        //TODO: verificar como ficou o response desse endpoint
        Mono<SessionResponse> sessionResponseMono = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}/isSeatOccupied/")
                        .queryParam("column", column)
                        .queryParam("line", line)
                        .build(sessionId))
                .retrieve()
                .bodyToMono(SessionResponse.class);

        //TODO: Após verificar o response, ajustar esse if
        if (column == 0 || line == 0) {
            throw new SeatUnavailableException("O assento informado não foi encontrado!");
        }
    }

    public void ocupySeat(String sessionId, int column, int line) {
        //TODO: verificar como vai se comportar em casos de erro no service de Session
        this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}/occupySeat/")
                        .queryParam("column", column)
                        .queryParam("line", line)
                        .build(sessionId))
                .retrieve()
                .bodyToMono(SessionResponse.class);
    }
}
