package br.com.itau.grupo4.ticketsmicroservice.model.exception;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(String message){
        super(message);
    }
}
