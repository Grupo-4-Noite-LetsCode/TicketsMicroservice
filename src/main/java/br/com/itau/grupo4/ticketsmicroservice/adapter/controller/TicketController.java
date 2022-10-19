package br.com.itau.grupo4.ticketsmicroservice.adapter.controller;

import br.com.itau.grupo4.ticketsmicroservice.dto.CanceledTicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;


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
