package br.com.itau.grupo4.ticketsmicroservice.mapper;

import br.com.itau.grupo4.ticketsmicroservice.dto.BuyTicketsRequest;
import br.com.itau.grupo4.ticketsmicroservice.model.Ticket;

public class TicketMapper {

    public static Ticket convertBuyRequestToEntity(BuyTicketsRequest request) {
        Ticket ticket = new Ticket();
        ticket.setSessionId(request.getSessionId());
        ticket.setSeatColumn(request.getSeatColumn());
        ticket.setSeatRow(request.getSeatRow());
        ticket.setType(request.getType());

        return ticket;
    }
}
