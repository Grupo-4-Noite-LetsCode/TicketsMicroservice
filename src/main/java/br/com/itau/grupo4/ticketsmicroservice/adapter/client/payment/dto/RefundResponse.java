package br.com.itau.grupo4.ticketsmicroservice.adapter.client.payment.dto;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RefundResponse {
    private UUID paymentId;
    private String paymentStatus;
}
