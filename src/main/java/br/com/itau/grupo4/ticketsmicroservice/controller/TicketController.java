package br.com.itau.grupo4.ticketsmicroservice.controller;
import br.com.itau.grupo4.ticketsmicroservice.dto.BuyTicketsRequest;
import br.com.itau.grupo4.ticketsmicroservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    @PostMapping
    public ResponseEntity<Object> buyTickets(@RequestBody BuyTicketsRequest request) {
        service.buyTickets(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> findTicketById(@PathVariable UUID id){
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }
}
