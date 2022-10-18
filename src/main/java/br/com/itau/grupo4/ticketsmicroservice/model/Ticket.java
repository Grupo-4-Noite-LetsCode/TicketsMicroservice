package br.com.itau.grupo4.ticketsmicroservice.model;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Ticket {

    @Id
    private UUID id = UUID.randomUUID();

    private UUID sessionId;

    private String status;

    private String seatColumn;

    private String seatRow;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
}
