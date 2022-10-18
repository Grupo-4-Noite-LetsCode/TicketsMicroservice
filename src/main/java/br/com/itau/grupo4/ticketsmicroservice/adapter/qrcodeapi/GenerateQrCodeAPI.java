package br.com.itau.grupo4.ticketsmicroservice.adapter.qrcodeapi;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
@Service
public class GenerateQrCodeAPI {
    private WebClient webClient;

    public GenerateQrCodeAPI(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.qrserver.com/v1")
                .build();
    }

    public Mono<String> getQrCode(String url) {
        Mono<String> qrCodeMono = this.webClient
                .get()
                .uri("/create-qr-code/?data=".concat(url))
                .retrieve()
                .bodyToMono(String.class);
        return qrCodeMono;
    }
}




// https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=Example