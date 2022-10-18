package br.com.itau.grupo4.ticketsmicroservice.exception;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(String message){
        super(message);
    }
}
