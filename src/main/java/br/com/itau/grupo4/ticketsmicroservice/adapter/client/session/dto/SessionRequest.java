package br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionRequest {
    private String sessionId;
    private int seatColumn;
    private int seatLine;
}
