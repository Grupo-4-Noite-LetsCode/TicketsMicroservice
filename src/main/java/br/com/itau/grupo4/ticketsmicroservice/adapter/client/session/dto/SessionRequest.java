package br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SessionRequest {
    private UUID sessionId;
    private String seatColumn;
    private String seatRow;
}
