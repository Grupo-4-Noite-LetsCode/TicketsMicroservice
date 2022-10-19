package br.com.itau.grupo4.ticketsmicroservice.adapter.client.payment.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RefundResponse {
    private UUID paymentId;
    private String paymentStatus;
}
