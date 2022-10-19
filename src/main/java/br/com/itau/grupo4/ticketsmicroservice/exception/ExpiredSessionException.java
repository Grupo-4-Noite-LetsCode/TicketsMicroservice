package br.com.itau.grupo4.ticketsmicroservice.exception;

public class ExpiredSessionException extends RuntimeException{
    public ExpiredSessionException(String message){
        super(message);
    }
}
