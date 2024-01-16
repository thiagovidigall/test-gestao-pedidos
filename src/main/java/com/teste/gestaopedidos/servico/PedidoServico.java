package com.teste.gestaopedidos.servico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.gestaopedidos.entidade.Cliente;
import com.teste.gestaopedidos.entidade.Pedido;
import com.teste.gestaopedidos.exceptions.DataCadastroInvalidaException;
import com.teste.gestaopedidos.exceptions.NumeroControlePedidoDuplicadoException;
import com.teste.gestaopedidos.exceptions.NumeroDePedidosExcedidoException;
import com.teste.gestaopedidos.repositorio.PedidoRepositorio;
import com.teste.gestaopedidos.seguranca.ClienteResponse;
import com.teste.gestaopedidos.seguranca.PedidoRequest;
import com.teste.gestaopedidos.seguranca.PedidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PedidoServico {
    private final PedidoRepositorio repositorio;

    private Logger logger = Logger.getLogger(PedidoServico.class.getName());

    @Autowired
    public PedidoServico(PedidoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<PedidoResponse> listarTodas(Long numeroControle, String dataCadastro) {
        logger.info("listarTodas");

        List<Pedido> pedidosBd = Collections.emptyList();
        LocalDate dataParam = definirDataCadastro(dataCadastro);

        if(dataParam != null){
            var controleParam = numeroControle == null ? 0 : numeroControle;
            pedidosBd =  repositorio.findAllByDataCadastroOrNumero(controleParam, dataParam);
        } else if (numeroControle != null) {
            var pedido = repositorio.findByNumero(numeroControle);
            pedidosBd = pedido == null ? new ArrayList<Pedido>() : List.of(pedido);
        } else {
            pedidosBd =  repositorio.findAll();
        }

        return pedidosBd.stream().map(this::mapPedidoToPedidoResponse).toList();
    }

    public List<PedidoResponse> criarPedidos(MultipartFile arquivo) throws IOException {
        String pedidoStr = new BufferedReader(new InputStreamReader(arquivo.getInputStream()))
                .lines().parallel().collect(Collectors.joining("\n"));

        ObjectMapper mapper = new ObjectMapper();
        List<PedidoRequest> pedidos = new ArrayList<>();

        if(pedidoStr.startsWith("[") && pedidoStr.endsWith("]")){
            pedidos = Arrays.stream(mapper.readValue(pedidoStr, PedidoRequest[].class)).toList();
        } else {
            PedidoRequest pedido = mapper.readValue(pedidoStr, PedidoRequest.class);
            pedidos.add(pedido);
        }

        validarPedidos(pedidos);

        List<Pedido> pedidosDb = pedidos.stream().map(this::mapPedidoRequestToPedido).toList();
        var resultado = repositorio.saveAll(pedidosDb);
        return resultado.stream().map(this::mapPedidoToPedidoResponse).toList();
    }

    private void validarPedidos(List<PedidoRequest> pedidos) {
        if (pedidos.size() > 10) {
            throw new NumeroDePedidosExcedidoException("O arquivo deve conter no máximo 10 pedidos.");
        }

        var pedidosBd = repositorio.findAll();
        var numerosControleBd = pedidosBd.stream().map(Pedido::getNumero).toList();
        var numerosControlePedido = pedidos.stream().map(PedidoRequest::getNumero).toList();

        List<Long> controlesJaCadastrados = numerosControlePedido.stream()
                .filter(numeroPedido -> numerosControleBd.contains(numeroPedido))
                .toList();

        if (controlesJaCadastrados.size() > 0) {
            throw new NumeroControlePedidoDuplicadoException("O arquivo com o(s) pedido(s) não pode(em) ser cadastrado(s) pois exite(m) o(s) seguinte(s) número(s) de controle cadastrado(s): " + controlesJaCadastrados.toString());
        }
    }

    private PedidoResponse mapPedidoToPedidoResponse(Pedido pedido) {
        var dataStr = pedido.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return PedidoResponse
                .builder()
                .numero(pedido.getNumero())
                .produto(pedido.getProduto())
                .valor(pedido.getValor())
                .total(pedido.getTotal())
                .quantidade(pedido.getQuantidade())
                .dataCadastro(dataStr)
                .cliente(ClienteResponse
                        .builder()
                        .id(pedido.getCliente().getId())
                        .nome(pedido.getCliente().getNome())
                        .build())
                .build();
    }

    private Pedido mapPedidoRequestToPedido(PedidoRequest pedidoRequest) {
        return Objects.nonNull(pedidoRequest)
                ? Pedido
                    .builder()
                    .id(null)
                    .numero(pedidoRequest.getNumero())
                    .produto(pedidoRequest.getProduto())
                    .valor(pedidoRequest.getValor())
                    .total(calcularTotalComDesconto(pedidoRequest.getQuantidade(), pedidoRequest.getValor()))
                    .quantidade(pedidoRequest.getQuantidade())
                    .dataCadastro(definirDataCadastro(pedidoRequest.getDataCadastro()))
                    .cliente(Cliente.builder().id(pedidoRequest.getClienteId()).build())
                    .build()
                : Pedido.class.cast(null);
    }

    private LocalDate definirDataCadastro(String dataCadastro){
        try {
            if(Objects.isNull(dataCadastro)){
                return null;
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(dataCadastro, formatter);
            }
        } catch (Exception e) {
            throw new DataCadastroInvalidaException("A data informada é inválida");
        }
    }

    private BigDecimal calcularTotalComDesconto(Integer quantidade, BigDecimal valor) {
        var qtde = quantidade == null ? 1 : quantidade;
        var total = calcularTotal(qtde, valor);
        return calcularDeconto(qtde, total);
    }

    private Double calcularTotal(Integer qtde, BigDecimal valor) {
        return qtde * valor.doubleValue();
    }

    private BigDecimal calcularDeconto(Integer qtde, Double total) {
        if (qtde >= 10){
            return BigDecimal.valueOf(total * 0.9);
        } else if (qtde >= 5) {
            return BigDecimal.valueOf(total * 0.95);
        } else {
            return BigDecimal.valueOf(total);
        }
    }
}
