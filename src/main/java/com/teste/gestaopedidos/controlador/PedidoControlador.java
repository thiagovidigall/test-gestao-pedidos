package com.teste.gestaopedidos.controlador;

import com.teste.gestaopedidos.seguranca.PedidoResponse;
import com.teste.gestaopedidos.servico.PedidoServico;
import com.teste.gestaopedidos.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pedido/v1")
@Tag(name = "Pedido", description = "EndPoints para Gestão dos Pedidos")
public class PedidoControlador {
    
    @Autowired
    private PedidoServico servico;

    @GetMapping(value = "/listar",
        produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "Lista todos os pedidos", description = "Lista todos os pedidos",
        tags = {"Pedido"},
        responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                content = {
                    @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = PedidoResponse.class))
                    )
                }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        }
    )
    public ResponseEntity<List<PedidoResponse>>  listaTodas(
            @Parameter(description = "Número do controle") @RequestParam(required = false) Long numero,
            @Parameter(description = "Data de cadastro") @RequestParam(required = false) String data) throws Exception {

        var resultado = servico.listarTodas(numero, data);
        return resultado.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado) : ResponseEntity.ok(resultado);
    }

    @PostMapping(value = "/pedido-upload/criar",
            produces = { MediaType.APPLICATION_JSON },
            consumes = { MediaType.MULTIPART })
    @Operation(summary = "Criar um novo Pedido", description = "Criar um novo Pedido passando um arquivo em JSON ou XML representando o Pedido",
            tags = {"Pedido"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PedidoResponse.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<PedidoResponse>> criarPedidoUpload(@RequestParam("arquivo") MultipartFile arquivo) throws Exception {
        var pedidos = servico.criarPedidos(arquivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidos);
    }
}
