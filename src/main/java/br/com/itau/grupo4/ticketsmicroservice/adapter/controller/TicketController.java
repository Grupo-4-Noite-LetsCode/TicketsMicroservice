package br.com.itau.grupo4.ticketsmicroservice.adapter.controller;


import br.com.itau.grupo4.ticketsmicroservice.adapter.client.qrcodeapi.GenerateQrCodeAPI;
import br.com.itau.grupo4.ticketsmicroservice.dto.Base64Response;
import br.com.itau.grupo4.ticketsmicroservice.dto.CanceledTicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    private final GenerateQrCodeAPI qrCodeAPI;


    @GetMapping("/qr/{id}")
    public Mono<Base64Response> generateQrCode(@PathVariable UUID id) {
        Base64Response response = Base64Response.builder().base64(ticketService.generateQrCode(id)).build();
        return Mono.just(response);
    }

    @PatchMapping("/cancelamento/{id}")
    public ResponseEntity<CanceledTicketResponse> cancelTicket(@PathVariable("id") UUID id){
        return ResponseEntity.ok(ticketService.cancel(id));
    }

    @GetMapping("{id}")
    public Mono<TicketResponse> findTicketById(@PathVariable UUID id){
        var response = ticketService.findById(id);
        return Mono.just(response);

    }
}
