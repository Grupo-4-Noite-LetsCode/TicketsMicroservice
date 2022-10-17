package br.com.itau.grupo4.ticketsmicroservice.repository;

import br.com.itau.grupo4.ticketsmicroservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

}
