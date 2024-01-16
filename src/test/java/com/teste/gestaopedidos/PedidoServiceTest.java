package com.teste.gestaopedidos;

import com.teste.gestaopedidos.entidade.Cliente;
import com.teste.gestaopedidos.entidade.Pedido;
import com.teste.gestaopedidos.excecoes.NumeroControleJaExistenteNoBancoDeDadosException;
import com.teste.gestaopedidos.repositorio.PedidoRepositorio;
import com.teste.gestaopedidos.seguranca.ClienteResponse;
import com.teste.gestaopedidos.seguranca.PedidoResponse;
import com.teste.gestaopedidos.servico.PedidoServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PedidoServiceTest {

    @Mock
    private PedidoRepositorio repositorio;

    @InjectMocks
    private PedidoServico servico;

    private Cliente cliente1;
    private Pedido pedido1;

    private PedidoResponse pedidoResponse;
    private ClienteResponse clienteResponse;
    private List<Pedido> listaPedidos;

    private List<PedidoResponse> listaResponse;
    private MockMultipartFile arquivo;

    @BeforeEach
    public void configuracao(){
        cliente1 = Cliente
                .builder()
                .id(3L)
                .nome("Cliente 3")
                .build();

        pedido1 = Pedido
                .builder()
                .numero(9913L)
                .produto("valido 2")
                .quantidade(1)
                .valor(new BigDecimal(2))
                .total(new BigDecimal(2))
                .dataCadastro(LocalDate.now())
                .cliente(cliente1)
                .build();
        listaPedidos = new ArrayList<>();
        listaPedidos.add(pedido1);

        pedidoResponse = PedidoResponse
                .builder()
                .numero(9913L)
                .produto("valido 2")
                .quantidade(1)
                .valor(new BigDecimal(2))
                .total(new BigDecimal(2))
                .dataCadastro(LocalDate.now().toString())
                .cliente(ClienteResponse.builder().id(3L).build())
                .build();
        listaResponse = new ArrayList<>();
        listaResponse.add(pedidoResponse);

        arquivo = new MockMultipartFile(
                "file",
                "pedido.json",
                "multipart/form-data",
                "[{\"numero\":9913,\"produto\":\"valido 2\",\"valor\":2,\"clienteId\":3}]".getBytes()
        );
    }

    @DisplayName("Teste ao criar os pedidos por arquivo")
    @Test
    void testGivenCriandoPedidos_ComArquivo() throws Exception {
        //Given / Arrange
        given(repositorio.findAll()).willReturn(Collections.emptyList());//
        given(repositorio.saveAll(listaPedidos)).willReturn(listaPedidos);

        //When / Act
        listaResponse = servico.criarPedidos(arquivo);

        //Then / Assert
        assertNotNull(listaResponse);
        assertEquals(9913L, listaResponse.get(0).getNumero());
    }

//    @DisplayName("Teste ao Cadastrar um pedido com número de controle já existente no banco")
//    @Test
//    void testGivenNumeroControleJaExistente_AoCriarPedidos_ComArquivo() throws Exception {
//        //Given / Arrange
//        given(repositorio.findByNumero(anyLong())).willReturn(pedido1);//
//
//        //When / Act
//        assertThrows(NumeroControleJaExistenteNoBancoDeDadosException.class, () -> { servico.criarPedidos(arquivo); });
//
//        //Then / Assert
//        verify(repositorio, any()).save(any(Pedido.class));
//    }
}
