package br.com.itau.grupo4.ticketsmicroservice.dto;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CanceledTicketResponse {
    private UUID ticketId;
    private String sessionId;
    private TicketStatus status;
}
