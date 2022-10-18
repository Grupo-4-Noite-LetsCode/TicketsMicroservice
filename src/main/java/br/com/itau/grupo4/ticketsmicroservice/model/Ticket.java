package br.com.itau.grupo4.ticketsmicroservice.model;

import br.com.itau.grupo4.ticketsmicroservice.enums.TicketType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID sessionId;
    //TODO: Depois que definir os status, colocar o valor default de status para criação
    private String status;
    private String seatColumn;
    private String seatRow;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
}
