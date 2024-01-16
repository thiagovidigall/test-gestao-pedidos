package com.teste.gestaopedidos.repositorio;

import com.teste.gestaopedidos.entidade.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    @Query(
            """
                SELECT ped
                FROM Pedido ped
                WHERE (:cadastro = NULL OR ped.dataCadastro = :cadastro)
                AND (:numero = 0 OR ped.numero = :numero)
                """
    )
    List<Pedido> findAllByDataCadastroOrNumero(
            @Param("numero") Long numero,
            @Param("cadastro") LocalDate cadastro);

    Pedido findByNumero(Long numero);
}
