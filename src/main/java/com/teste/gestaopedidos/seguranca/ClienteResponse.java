package com.teste.gestaopedidos.seguranca;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;
}
