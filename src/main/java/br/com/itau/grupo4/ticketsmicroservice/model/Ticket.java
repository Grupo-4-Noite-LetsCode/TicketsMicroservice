package br.com.itau.grupo4.ticketsmicroservice.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Ticket {

    private UUID id;
    private UUID sessionId;
    private String status;
    private String seatColumn;
    private String seatRow;

}
