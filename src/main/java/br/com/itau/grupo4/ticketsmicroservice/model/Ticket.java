package br.com.itau.grupo4.ticketsmicroservice.model;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    private String sessionId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.PEDIDO_RECEBIDO;

    private int seatColumn;

    private int seatLine;

    @Enumerated(EnumType.STRING)
    private TicketType type;
}
