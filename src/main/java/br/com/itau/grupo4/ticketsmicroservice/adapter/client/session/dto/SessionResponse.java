package br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionResponse {
    private UUID sessionId;
}
