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
    private UUID sessionId;
    private TicketStatus status;
    private String seatColumn;
    private String seatRow;
    private TicketType type;
}
