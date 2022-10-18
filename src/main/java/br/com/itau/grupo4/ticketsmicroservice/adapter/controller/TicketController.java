package br.com.itau.grupo4.ticketsmicroservice.adapter.controller;

import br.com.itau.grupo4.ticketsmicroservice.adapter.qrcodeapi.GenerateQrCodeAPI;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;
    private final GenerateQrCodeAPI qrCodeAPI;
    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> findTicketById(@PathVariable UUID id){
        var response = service.findById(id);
        return ResponseEntity.ok(response);

    }

    @GetMapping()
    public Mono<String> generateQrCode(){
        var response = qrCodeAPI.getQrCode("https://youtube.com");
        return response;

    }
}
