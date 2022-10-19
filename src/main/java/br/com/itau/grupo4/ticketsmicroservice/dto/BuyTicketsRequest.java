package br.com.itau.grupo4.ticketsmicroservice.dto;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import lombok.Data;


@Data
public class BuyTicketsRequest {
    private String sessionId;
    private int seatColumn;
    private int seatLine;
    private TicketType type;
}

