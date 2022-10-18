package br.com.itau.grupo4.ticketsmicroservice.controller;

import br.com.itau.grupo4.ticketsmicroservice.dto.CanceledTicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
