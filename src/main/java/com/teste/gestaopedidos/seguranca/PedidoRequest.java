package com.teste.gestaopedidos.seguranca;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    private Long numero;

    private String produto;

    private Integer quantidade;

    private BigDecimal valor;

    private String dataCadastro;

    private Long clienteId;
}
