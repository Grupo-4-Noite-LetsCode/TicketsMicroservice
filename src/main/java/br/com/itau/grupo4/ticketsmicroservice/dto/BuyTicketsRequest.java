package br.com.itau.grupo4.ticketsmicroservice.dto;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import lombok.Data;

import java.util.UUID;

@Data
public class BuyTicketsRequest {
    private UUID sessionId;
    private String seatColumn;
    private String seatRow;
    private TicketType ticketType;
}
