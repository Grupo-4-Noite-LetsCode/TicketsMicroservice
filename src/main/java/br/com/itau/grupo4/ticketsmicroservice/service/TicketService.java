package br.com.itau.grupo4.ticketsmicroservice.service;

import br.com.itau.grupo4.ticketsmicroservice.adapter.qrcodeapi.GenerateQrCodeAPI;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import br.com.itau.grupo4.ticketsmicroservice.model.Ticket;
import br.com.itau.grupo4.ticketsmicroservice.model.exception.TicketNotFoundException;
import br.com.itau.grupo4.ticketsmicroservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;
    private final GenerateQrCodeAPI qrCodeAPI;

    public TicketResponse findById(UUID id) {
        var model = repository.findById(id).orElseThrow(()-> new TicketNotFoundException("O ticket não existe!"));
        return modelToResponse(model);
    }

    private TicketResponse modelToResponse(Ticket model) {
        return TicketResponse.builder()
                .id(model.getId())
                .sessionId(model.getSessionId())
                .status(model.getStatus())
                .seatColumn(model.getSeatColumn())
                .seatRow(model.getSeatRow())
                .type(model.getType())
                .build();
    }

    public String generateQrCode(UUID id){
        findById(id);
        String url = "http://localhost:8080/tickets/" + id;
        return qrCodeAPI.getQrCode(url);
    }
}