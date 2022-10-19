package br.com.itau.grupo4.ticketsmicroservice.adapter.client.payment.dto;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RefundRequest {
    private UUID ticketId;
    private TicketStatus status;
}
