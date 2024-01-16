package com.teste.gestaopedidos.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CampoNuloException extends RuntimeException {
    public CampoNuloException(String message) {
        super(message);
    }
}
