package com.teste.gestaopedidos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NumeroDePedidosExcedidoException extends RuntimeException {
    public NumeroDePedidosExcedidoException(String message) {
        super(message);
    }
}
