package br.com.itau.grupo4.ticketsmicroservice.dto;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TicketResponse {

    private UUID id;
    private String sessionId;
    private TicketStatus status;
    private int seatColumn;
    private int seatRow;
    private TicketType type;
}
