package com.teste.gestaopedidos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse implements Serializable {
    private static final long serialVesionUID = 1L;

    private Date data;
    private String mensagem;
    private String detalhes;
}
