package br.com.itau.grupo4.ticketsmicroservice.model;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID sessionId;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    private String seatColumn;
    private String seatRow;
    @Enumerated(EnumType.STRING)
    private TicketType type;
}
