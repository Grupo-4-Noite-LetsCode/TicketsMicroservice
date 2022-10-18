package br.com.itau.grupo4.ticketsmicroservice.service;

import br.com.itau.grupo4.ticketsmicroservice.dto.BuyTicketsRequest;
import br.com.itau.grupo4.ticketsmicroservice.mapper.TicketMapper;
import br.com.itau.grupo4.ticketsmicroservice.model.Ticket;
import br.com.itau.grupo4.ticketsmicroservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public void buyTickets(BuyTicketsRequest request) {
        verifySessionIsAvailable(request.getSessionId());
        verifySeatIsAvailable();//passar os assento

        Ticket ticket = TicketMapper.convertBuyRequestToEntity(request);
        ticketRepository.save(ticket);

        ocupySeat();//enviar assento
        sendPayment(ticket);
        sendEmail();
    }

    private void verifySessionIsAvailable(UUID sessionId) {
        //TODO: Verificar no service de session se ela é válida
        // Se for, retorna pro método comprar
        // se não for, lançar exceção aqui
    }

    private void verifySeatIsAvailable() {//vai receber o assento e enviar conforme o service de Session precisa
        //TODO: Verificar no service de session se o assento está disponível
        // Se estiver, retorna pro método comprar
        // se não, lançar exceção aqui
    }

    private void ocupySeat() {//vai receber o assento e enviar conforme o service de Session precisa
        //TODO: Ocupar o assento no service de session
        // se não der certo, como exibe a exception?
    }

    private void sendPayment(Ticket ticket) {
        //TODO: Enviar ticket para o service de Payments
    }


    private void sendEmail() {
        //TODO: Comunicar com service Notifications para enviar o e-mail "aguardando pagamento"
    }
}
