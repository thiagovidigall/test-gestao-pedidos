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

    @NotNull(message = "O número do pedido não pode ser nulo.")
    private Long numero;

    @NotNull(message = "O nome do produto não pode ser nulo.")
    private String produto;

    private Integer quantidade;

    @NotNull(message = "O valor do pedido não pode ser nulo.")
    private BigDecimal valor;

    private String dataCadastro;

    @NotNull(message = "O cliente do pedido não pode ser nulo.")
    private Long clienteId;
}
