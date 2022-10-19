package br.com.itau.grupo4.ticketsmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Base64Response {
    private String base64;

}
