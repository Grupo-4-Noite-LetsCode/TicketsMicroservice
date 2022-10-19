package br.com.itau.grupo4.ticketsmicroservice.adapter.controller;

import br.com.itau.grupo4.ticketsmicroservice.dto.BuyTicketsRequest;
import br.com.itau.grupo4.ticketsmicroservice.adapter.client.qrcodeapi.GenerateQrCodeAPI;
import br.com.itau.grupo4.ticketsmicroservice.dto.Base64Response;
import br.com.itau.grupo4.ticketsmicroservice.dto.CanceledTicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;

import br.com.itau.grupo4.ticketsmicroservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final TicketService service;

    private final GenerateQrCodeAPI qrCodeAPI;


    @GetMapping("/qr/{id}")
    public Mono<Base64Response> generateQrCode(@PathVariable UUID id) {
        Base64Response response = Base64Response.builder().base64(service.generateQrCode(id)).build();
        return Mono.just(response);
    }

    @PatchMapping("/cancelamento/{id}")
    public ResponseEntity<CanceledTicketResponse> cancelTicket(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.cancel(id));
    }

    @PostMapping
    public ResponseEntity<Mono<TicketResponse>> buyTickets(@RequestBody BuyTicketsRequest request) {
        Mono<TicketResponse> response = service.buyTickets(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> findTicketById(@PathVariable UUID id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }
}
