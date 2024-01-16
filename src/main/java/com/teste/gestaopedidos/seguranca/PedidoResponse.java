package com.teste.gestaopedidos.seguranca;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long numero;
    private String produto;
    private Integer quantidade;
    private BigDecimal valor;
    private String dataCadastro;
    private BigDecimal total;
    private ClienteResponse cliente;
}
