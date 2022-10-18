package br.com.itau.grupo4.ticketsmicroservice.dto;

import java.util.UUID;

public class BuyTicketsRequest {
    private UUID sessionId;
    private String status;
    private String seatColumn;
    private String seatRow;
}
