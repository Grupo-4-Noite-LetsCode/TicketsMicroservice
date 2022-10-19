package br.com.itau.grupo4.ticketsmicroservice.adapter.qrcodeapi;

import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Base64;

@Service
public class GenerateQrCodeAPI {
    private WebClient webClient;

    public GenerateQrCodeAPI(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.qrserver.com/v1")
                .build();
    }

    public String getQrCode(String url) {
        Mono<byte[]> qrCodeMono = this.webClient
                .get()
                .uri("/create-qr-code/?data=".concat(url))
                .retrieve()
                .bodyToMono(byte[].class);

        return Base64.getEncoder().encodeToString(qrCodeMono.block());
    }
}




// https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=Example