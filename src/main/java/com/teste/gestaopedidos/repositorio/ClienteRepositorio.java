package com.teste.gestaopedidos.repositorio;

import com.teste.gestaopedidos.entidade.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

}
