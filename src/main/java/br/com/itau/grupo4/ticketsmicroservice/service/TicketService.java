package br.com.itau.grupo4.ticketsmicroservice.service;

import br.com.itau.grupo4.ticketsmicroservice.client.payment.dto.RefundRequest;
import br.com.itau.grupo4.ticketsmicroservice.client.payment.service.PaymentService;
import br.com.itau.grupo4.ticketsmicroservice.client.session.dto.SessionRequest;
import br.com.itau.grupo4.ticketsmicroservice.client.session.service.SessionService;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus;
import br.com.itau.grupo4.ticketsmicroservice.exception.TicketNotFoundException;
import br.com.itau.grupo4.ticketsmicroservice.model.Ticket;
import br.com.itau.grupo4.ticketsmicroservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;
    private final SessionService sessionService;
    private final PaymentService paymentService;

    public TicketResponse findById(UUID id) {
        var model = getById(id);
        return modelToResponse(model);
    }

    public void cancel(UUID id) {
        TicketResponse ticket = findById(id);

        try{
            unblockSeat(ticket);
            refundPayment(ticket);
        }catch (WebClientException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }finally {
            updateStatus(id, TicketStatus.CANCELADO);
        }
    }

    private void updateStatus(UUID id, TicketStatus status){
        Ticket ticket = getById(id);
        ticket.setStatus(status);
        repository.save(ticket);
    }

    private Ticket getById(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new TicketNotFoundException("Ticket não existente."));
    }

    private void unblockSeat(TicketResponse ticket){
        SessionRequest request = SessionRequest.builder()
                .sessionId(ticket.getSessionId())
                .seatColumn(ticket.getSeatColumn())
                .seatRow(ticket.getSeatRow())
                .build();

        sessionService.unblockSeat(request);
    }

    private void refundPayment(TicketResponse ticket){
        RefundRequest request = RefundRequest.builder()
                .ticketId(ticket.getId())
                .status(ticket.getStatus())
                .build();

        paymentService.patchReimbursement(request);
    }
    private TicketResponse modelToResponse(Ticket model) {
        return TicketResponse.builder()
                .id(model.getId())
                .sessionId(model.getSessionId())
                .status(model.getStatus())
                .seatColumn(model.getSeatColumn())
                .seatRow(model.getSeatRow())
                .type(model.getType())
                .build();
    }

    private Ticket responseToModel(TicketResponse ticketResponse){
        return Ticket.builder()
                .id(ticketResponse.getId())
                .sessionId(ticketResponse.getSessionId())
                .status(ticketResponse.getStatus())
                .seatColumn(ticketResponse.getSeatColumn())
                .seatRow(ticketResponse.getSeatRow())
                .type(ticketResponse.getType())
                .build();
    }
}