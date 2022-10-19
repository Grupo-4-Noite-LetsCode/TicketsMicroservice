package br.com.itau.grupo4.ticketsmicroservice.exception;

public class SessionNotFoundException extends RuntimeException{
    public SessionNotFoundException(String message){
        super(message);
    }
}
