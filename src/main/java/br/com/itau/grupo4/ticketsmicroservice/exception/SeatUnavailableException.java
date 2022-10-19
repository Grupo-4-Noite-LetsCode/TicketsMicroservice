package br.com.itau.grupo4.ticketsmicroservice.exception;

public class SeatUnavailableException extends RuntimeException{
    public SeatUnavailableException(String message){
        super(message);
    }
}