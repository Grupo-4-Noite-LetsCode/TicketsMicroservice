package br.com.itau.grupo4.ticketsmicroservice.service;

import br.com.itau.grupo4.ticketsmicroservice.dto.BuyTicketsRequest;
import br.com.itau.grupo4.ticketsmicroservice.dto.TicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.mapper.TicketMapper;

import br.com.itau.grupo4.ticketsmicroservice.adapter.client.qrcodeapi.GenerateQrCodeAPI;
import br.com.itau.grupo4.ticketsmicroservice.adapter.client.payment.dto.RefundRequest;
import br.com.itau.grupo4.ticketsmicroservice.adapter.client.payment.service.PaymentService;
import br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.dto.SessionRequest;
import br.com.itau.grupo4.ticketsmicroservice.adapter.client.session.service.SessionService;
import br.com.itau.grupo4.ticketsmicroservice.dto.CanceledTicketResponse;
import br.com.itau.grupo4.ticketsmicroservice.exception.TicketNotFoundException;

import br.com.itau.grupo4.ticketsmicroservice.model.Ticket;
import br.com.itau.grupo4.ticketsmicroservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.UUID;

import static br.com.itau.grupo4.ticketsmicroservice.enums.TicketStatus.*;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;
    private final SessionService sessionService;
    private final PaymentService paymentService;
    private final GenerateQrCodeAPI qrCodeAPI;

    public void buyTickets(BuyTicketsRequest request) {
        String sessionId = request.getSessionId();
        int seatLine = request.getSeatColumn();
        int seatColumn = request.getSeatLine();

        sessionService.verifySessionIsAvailable(sessionId);
        sessionService.verifySeatIsAvailable(sessionId, seatLine, seatColumn);

        Ticket ticket = TicketMapper.convertBuyRequestToEntity(request);
        repository.save(ticket);

        sessionService.ocupySeat(sessionId, seatLine, seatColumn);
        sendPayment(ticket);
        sendEmail();
    }

    private void sendPayment(Ticket ticket) {
        //TODO: Enviar ticket para o service de Payments
    }


    private void sendEmail() {
        //TODO: Comunicar com service Notifications para enviar o e-mail "aguardando pagamento"
    }

    public TicketResponse findById(UUID id) {
        var model = repository.findById(id).orElseThrow(()-> new TicketNotFoundException("O ticket não existe!"));
        return modelToResponse(model);
    }

    public CanceledTicketResponse cancel(UUID id) {
        Ticket ticket = getById(id);

        try{
            unblockSeat(ticket);
            refundPayment(ticket);
        }catch (WebClientException e){
            throw new RuntimeException(e.getLocalizedMessage()); //? Personalizar exception?
        }finally {
            ticket.setStatus(CANCELADO); // ?
            repository.save(ticket);
        }

        return modelToCanceled(ticket);
    }

    private Ticket getById(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new TicketNotFoundException("Ticket não existente."));
    }

    private void unblockSeat(Ticket ticket){
        SessionRequest request = SessionRequest.builder()
                .sessionId(ticket.getSessionId())
                .seatColumn(ticket.getSeatColumn())
                .seatLine(ticket.getSeatLine())
                .build();

        sessionService.unblockSeat(request);
    }

    private void refundPayment(Ticket ticket){
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
                .seatLine(model.getSeatLine())
                .type(model.getType())
                .build();
    }

    public String generateQrCode(UUID id){
        findById(id);
        String url = "http://localhost:8080/tickets/" + id;
        return qrCodeAPI.getQrCode(url);
    }

    private CanceledTicketResponse modelToCanceled(Ticket ticket){
        return CanceledTicketResponse.builder()
                .ticketId(ticket.getId())
                .sessionId(ticket.getSessionId())
                .status(ticket.getStatus())
                .build();
    }
}
