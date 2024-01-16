package com.teste.gestaopedidos.entidade;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" })
public class Pedido {

    @PrePersist
    private void init() {
        this.dataCadastro = Objects.isNull(this.dataCadastro) ? LocalDate.now() : this.dataCadastro;
        this.quantidade = Objects.isNull(this.quantidade) ? 1 : this.quantidade;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_controle")
    private Long numero;

    @Column(name = "nome_produto")
    private String produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "valor_total")
    private BigDecimal total;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id", referencedColumnName = "id")
    private Cliente cliente;
}
