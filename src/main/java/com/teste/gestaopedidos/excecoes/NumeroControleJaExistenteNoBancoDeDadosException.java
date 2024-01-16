package com.teste.gestaopedidos.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NumeroControleJaExistenteNoBancoDeDadosException extends RuntimeException {
    public NumeroControleJaExistenteNoBancoDeDadosException(String message) {
        super(message);
    }
}
